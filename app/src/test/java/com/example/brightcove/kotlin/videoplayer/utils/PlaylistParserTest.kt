package com.example.brightcove.kotlin.videoplayer.utils

import org.junit.Assert.assertNotNull
import org.junit.Test

const val TEST_PLAYLIST_JSON = """
    {
   "accountId":"5420904993001",
   "policyKey":"BCpkADawqM1RJu5c_I13hBUAi4c8QNWO5QN2yrd_OgDjTCVsbILeGDxbYy6xhZESTFi68MiSUHzMbQbuLV3q-gvZkJFpym1qYbEwogOqKCXK622KNLPF92tX8AY9a1cVVYCgxSPN12pPAuIM",
   "catalogAssetList":[
      {
         "type":"VIDEO",
         "id":"6031901107001",
         "description":"The Big Buck Bunny Video"
      },
      {
         "type":"VIDEO",
         "id":"5421538222001",
         "description":""
      },
      {
         "type":"VIDEO",
         "id":"5421538244001",
         "description":""
      },
      {
         "type":"VIDEO",
         "id":"5421543459001",
         "description":""
      },
      {
         "type":"VIDEO",
         "id":"5421531913001",
         "description":""
      }
   ]
}
"""

class PlaylistParserTest {

    @Test
    fun testParser() {
        val playerPlaylist = PlaylistParser.parsePlaylist(TEST_PLAYLIST_JSON)
        assertNotNull(playerPlaylist)
    }
}