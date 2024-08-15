package com.iot_bk.iot_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {


    MQTTHelper mqttHelper;

    TextView txtTemperature, txtHumid;

    LabeledSwitch relay1, relay2, relay3, relay4, relay5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        startMQTT();

        txtTemperature = findViewById(R.id.txtTemperature);
        txtHumid = findViewById(R.id.txtHumid);

        relay1 = findViewById(R.id.relay1);
        relay2 = findViewById(R.id.relay2);
        relay3 = findViewById(R.id.relay3);
        relay4 = findViewById(R.id.relay4);
        relay5 = findViewById(R.id.relay5);

        relay1.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true) {
                    sendDataMQTT("sonsonha/feeds/status", "!Relay1:ON#");
                } else {
                    sendDataMQTT("sonsonha/feeds/status", "!Relay1:OFF#");
                }
            }
        });

        relay2.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true) {
                    sendDataMQTT("sonsonha/feeds/status", "!Relay2:ON#");
                } else {
                    sendDataMQTT("sonsonha/feeds/status", "!Relay2:OFF#");
                }
            }
        });

        relay3.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true) {
                    sendDataMQTT("sonsonha/feeds/status", "!Relay3:ON#");
                } else {
                    sendDataMQTT("sonsonha/feeds/status", "!Relay3:OFF#");
                }
            }
        });

        relay4.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true) {
                    sendDataMQTT("sonsonha/feeds/status", "!Relay4:ON#");
                } else {
                    sendDataMQTT("sonsonha/feeds/status", "!Relay4:OFF#");
                }
            }
        });

        relay5.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true) {
                    sendDataMQTT("sonsonha/feeds/status", "!Relay5:ON#");
                } else {
                    sendDataMQTT("sonsonha/feeds/status", "!Relay5:OFF#");
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(12345678);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){
        }
    }

    public void startMQTT() {
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("TEST", topic + "***" + message.toString());
                if(topic.contains("cambien1")) {
                    txtTemperature.setText(message.toString() + "℃");
                }
                else if(topic.contains("cambien2")) {
                    txtHumid.setText(message.toString() + "%");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}