package com.example.fitness.utils

import android.R
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.util.Xml
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.fitness.Activity.DataSaveActivity
import org.xmlpull.v1.XmlSerializer
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.StringWriter
import java.util.ArrayList
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class LocationService : Service() {
    private lateinit var mLocationManager: LocationManager
    private lateinit var mLocationListeners: LocationListener
    private var arrLoc: ArrayList<Location> = ArrayList()

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startForegroundService()
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        initializeLocationManager()
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.1f, mLocationListeners)
    }

    private fun startForegroundService() {
        val CHANEL = "Fitness"
        val channel = NotificationChannel(CHANEL, CHANEL, NotificationManager.IMPORTANCE_LOW)
        //channel.enableVibration(false)

        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = Notification.Builder(this, CHANEL)
            .setContentText("Идёт запись")
            .setContentTitle("Fitness")
            .setSmallIcon(R.mipmap.sym_def_app_icon)
        startForeground(1001, notification.build())
    }

    fun CreateNotify(text: String)
    {
        val CHANEL = "Fitness"
        val notification = Notification.Builder(this, CHANEL)
            .setContentText(text)
            .setContentTitle("Fitness")
            .setSmallIcon(R.mipmap.sym_def_app_icon)
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(1001, notification.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationManager.removeUpdates(mLocationListeners)
        XmlWriter()
    }

    private fun initializeLocationManager() {
        mLocationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
        mLocationListeners = object: LocationListener {

            override fun onLocationChanged(location: Location) {
                arrLoc.add(location)
                var intent = Intent("location_update")
                println("Update")
                intent.putExtra("loc", location)
                sendBroadcast(intent)
                val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
                CreateNotify(simpleDateFormat.format(Date(arrLoc.last().time-arrLoc.first().time)))
            }

            override fun onProviderDisabled(provider: String) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

        }
    }
    fun XmlWriter(){

        val Format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val p = Format.format(Date(arrLoc[0].time))
        val xmlSerializer = Xml.newSerializer()
        val xmlString = xmlSerializer.document {
            element("gpx") {
                attribute("xsi:schemaLocation", "http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd")
                attribute("xmlns", "http://www.topografix.com/GPX/1/1")
                attribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
                attribute("xmlns:ns3", "http://www.garmin.com/xmlschemas/TrackPointExtension/v1")
                attribute("xmlns:ns2", "http://www.garmin.com/xmlschemas/GpxExtensions/v3")
                attribute("xmlns:ns1", "http://www.cluetrust.com/XML/GPXDATA/1/0")
                attribute("creator", "CompAs")
                attribute("version", "0.0.1")
                element("metadata"){
                    element("name", "Track")
                    element("desc", "Ordinary track")
                    element("author"){
                        element("name", "CompAs owner")
                    }
                }
                element("trk") {
                    element("trkseg"){
                        for (i in 0..arrLoc.size-1){
                            element("trkpt"){
                                attribute("lat", String.format(Locale.US,"%.6f", arrLoc[i].latitude))
                                attribute("lon", String.format(Locale.US,"%.6f", arrLoc[i].longitude))
                                element("time", Format.format(Date(arrLoc[i].time)))
                                element("ele", String.format(Locale.US,"%.1f", arrLoc[i].altitude))
                            }
                        }

                    }
                }
            }
        }
        var Doc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val Format2 = SimpleDateFormat("'Fitness'yyyyMMddHHmmss'.gpx'")
        val bw = BufferedWriter(
            FileWriter(Doc.toString()+"/"+Format2.format(Date(arrLoc[0].time)))
        )
        bw.write(xmlString)
        bw.close()

        val intent = Intent(this, DataSaveActivity::class.java)
        intent.putExtra("path", Doc.toString()+"/"+Format2.format(Date(arrLoc[0].time)))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun XmlSerializer.document(docName: String = "UTF-8",
                               xmlStringWriter: StringWriter = StringWriter(),
                               init: XmlSerializer.() -> Unit): String {
        startDocument(docName, true)
        xmlStringWriter.buffer.setLength(0) //  refreshing string writer due to reuse
        setOutput(xmlStringWriter)
        init()
        endDocument()
        return xmlStringWriter.toString()
    }

    //  element
    fun XmlSerializer.element(name: String, init: XmlSerializer.() -> Unit) {
        startTag("", name)
        init()
        endTag("", name)
    }

    //  element with attribute & content
    fun XmlSerializer.element(name: String,
                              content: String,
                              init: XmlSerializer.() -> Unit) {
        startTag("", name)
        init()
        text(content)
        endTag("", name)
    }

    //  element with content
    fun XmlSerializer.element(name: String, content: String) =
        element(name) {
            text(content)
        }

    //  attribute
    fun XmlSerializer.attribute(name: String, value: String) =
        attribute("", name, value)
}