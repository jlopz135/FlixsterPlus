package com.example.flixsterplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixsterplus.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException


fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"

private const val ACTORS_SEARCH = "https://api.themoviedb.org/3/person/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
class MainActivity : AppCompatActivity() {
    private val actor = mutableListOf<Actor>()
    private lateinit var rvPeople: RecyclerView
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        rvPeople = findViewById(R.id.rvPeople)

        val peopleAdapter = PeopleAdapter(this, actor)

        rvPeople.adapter = peopleAdapter
        rvPeople.layoutManager = GridLayoutManager(applicationContext,2)

        val client = AsyncHttpClient()
        client.get(ACTORS_SEARCH, object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(ContentValues.TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(ContentValues.TAG, "onSuccess: JSON data $json")

                try {
                    val parsedJson = createJson().decodeFromString(
                        GetNewResult.serializer(),
                        json.jsonObject.toString()
                    )
                    parsedJson.result?.let { list ->
                        actor.addAll(list)

                        peopleAdapter.notifyDataSetChanged()
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")

                }
            }
        })
    }
}