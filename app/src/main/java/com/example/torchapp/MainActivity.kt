@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.example.torchapp

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.torchapp.ui.theme.TorchAppTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorchApplication(LocalContext.current)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TorchApplication(context:Context){
//    remember function for on/off providing
    val torchStatus = remember {
        mutableStateOf(false)
    }
    val torchMsg = remember {
        mutableStateOf("Off !!")
    }
    Column(
//        There part of center a Button
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp)
            .size(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "FalshLight is ${torchMsg.value}",
            fontWeight = FontWeight.Bold
            )
//        These part of Switching button
        Switch(checked = torchStatus.value, onCheckedChange =
        {
        torchStatus.value = it
//            These part of Cemera (lateinitvar..?)
            lateinit var cameraManager: CameraManager
            lateinit var cameraID:String

//            Initilizing our cameraManger Setup
             cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

            try{
                cameraID = cameraManager.cameraIdList[0]
            }catch(e:Exception){
                e.printStackTrace()
            }

//            that's a condition part
            if(torchStatus.value) {
                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        cameraManager.setTorchMode(cameraID, true)
                        Toast.makeText(context, "FlashLight is On!!", Toast.LENGTH_LONG).show()

//                        On/off value is changed
                        torchMsg.value = "On !!"

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
                else{
                    try {
                        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                            cameraManager.setTorchMode(cameraID,false)
                            Toast.makeText(context, "FlashLight is Off!!", Toast.LENGTH_LONG).show()

//                            on/off value is changed
                            torchMsg.value = "Off !!"
                    }
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }
        })

    }
}

