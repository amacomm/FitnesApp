package com.example.fitness.utils

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.location.LocationManager
import android.os.IBinder
import androidx.activity.ComponentActivity
import com.example.fitness.client.apis.FollowsApi
import com.example.fitness.client.apis.TrackRecordsApi
import com.example.fitness.client.apis.UsersApi
import com.example.fitness.client.infrastructure.ServerException
import com.example.fitness.client.models.AuthRequest
import com.example.fitness.client.models.TrackRecordDto
import com.example.fitness.client.models.UserDto
import com.example.fitness.client.models.UserFollow
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.Date
import kotlin.concurrent.thread

class ApiService: Service() {
    lateinit var userApi: UsersApi
    lateinit var trackApi: TrackRecordsApi
    lateinit var followsApi: FollowsApi
    lateinit var user: UserDto
    lateinit var token: String
    val url = "https://dkz1z6k5-7125.euw.devtunnels.ms/"

    override fun onCreate() {
        super.onCreate()
        userApi = UsersApi(url)
        trackApi = TrackRecordsApi(url)
        followsApi = FollowsApi(url)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val page = intent.extras!!.get("page") as String
        thread {
        when(page){
            "profile"-> {
                SendUserData()
            }
            else->{
                val path = intent.extras!!.get("path") as String
                val auth = userApi.apiUsersLoginPost(AuthRequest("user", "user"))
                token = auth.accessToken!!
                userApi.addAccessToken(auth)
                followsApi.addAccessToken(auth)
                trackApi.addAccessToken(auth)
                user = userApi.apiUsersMeGet()
                SendUserData()
                SendHistory()
                SendUserTracks()
            }
        }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun SendUserData(){
        var intent = Intent("userdata")
        intent.putExtra("username", "${user.firstName} ${user.lastName}")
        intent.putExtra("usergender", user.gender)
        intent.putExtra("userdate", user.dateOfBirth.toString())
        sendBroadcast(intent)
    }

    fun loadToken(tokenPath: String): Boolean {
        val file = File(tokenPath)
        if (file.exists()) {
            val br = BufferedReader(FileReader(file))
            token = br.readLine()
                thread{
                    try{
                        userApi.addAccessToken(token)
                        followsApi.addAccessToken(token)
                        trackApi.addAccessToken(token)
                        user = userApi.apiUsersMeGet()
                    } catch (e: ServerException){
                    }
                }
            return true
        }
        return false
    }

    fun saveToken(Path:String,  Login: String, Password: String){
        var auth = userApi.apiUsersLoginPost(AuthRequest(Login, Password))
        val writer = FileWriter(Path)
        writer.append(auth.accessToken)
        writer.flush()
        writer.close()
    }

    fun SendHistory(){
        val follows = user.id?.let { followsApi.apiFollowsIdGet(it) }
        var intent: Intent
        if (follows != null) {
            follows.forEach {
                (it.followedUserId?.let { it1 -> trackApi.apiTrackRecordsUserUserIdGet(it1) })?.forEach {
                    intent = Intent("history")
                    intent.putExtra("username", it.gpxData!!.creator)
                    intent.putExtra("date", it.gpxData!!.points!![0].time)
                    intent.putExtra("distance", it.gpxData!!.distance2D)
                    intent.putExtra("speed", it.gpxData!!.avgSpeed)
                    intent.putExtra("time", it.gpxData!!.allTime.toString())
                    sendBroadcast(intent)
                }
            }
        }
        (user.id?.let { trackApi.apiTrackRecordsUserUserIdGet(it) })?.forEach {
            intent = Intent("history")
            intent.putExtra("username", it.gpxData!!.creator)
            intent.putExtra("date", it.gpxData!!.points!![0].time.toString())
            intent.putExtra("distance", it.gpxData!!.distance2D)
            intent.putExtra("speed", it.gpxData!!.avgSpeed)
            intent.putExtra("time", it.gpxData!!.allTime.toString())
            sendBroadcast(intent)
        }
    }
    fun SendUserTracks(){
        var intent: Intent
        (user.id?.let { trackApi.apiTrackRecordsUserUserIdGet(it) })?.forEach {
            intent = Intent("usertracks")
            intent.putExtra("username", it.gpxData!!.creator)
            intent.putExtra("date", it.gpxData!!.points!![0].time.toString())
            intent.putExtra("distance", it.gpxData!!.distance2D)
            intent.putExtra("speed", it.gpxData!!.avgSpeed)
            intent.putExtra("time", it.gpxData!!.allTime.toString())
            sendBroadcast(intent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

//    fun getSubscription(): Array<UserDto> {
//        var follows = user.id?.let { followsApi.apiFollowsIdGet(it) }
//        follows.forEach {
//            it.followingUserId
//        }
//    }
}