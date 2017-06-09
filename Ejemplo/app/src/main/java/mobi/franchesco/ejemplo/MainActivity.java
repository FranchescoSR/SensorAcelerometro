package mobi.franchesco.ejemplo;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;//Con esto accedemos a los sensores de nuestro dispositivo
    Sensor sensor;//variable oara representar al nuestro sensor
    SensorEventListener sensorEventListener;//nos avisara cuando nosotros movamos nuestro dispositivo
    int movimineto=0;//Declaramos nuestra variable movimiento

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);//llamamos al sensor
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//llamamos al el tipo de sensor en este caso es el acelerometro

        if(sensor == null)
            finish();//con esto decimos si nuestro movil cuenta con este tipo de sensor sino finalizara la accion

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                
                //aqui estamos declarando las variables que android nos da en un arreglo en donde x=0,y=1 y z=2
                float x = sensorEvent.values[0]; /* 0 = x --- y = 1 --- z = 2 */

                if (x<-5 && movimineto == 0 ){
                    movimineto++;
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);//cuando se gira a la derecha coge un valor aprox de -5
                    /*System.out.print("Movimiento Verticalmente a la DERECHA");*/
                }
                else if (x>5 && movimineto==1){
                    movimineto++;
                    getWindow().getDecorView().setBackgroundColor(Color.RED);//de esta manera al reconocer el movimiento el color del fondo de la pantalla se cambiara
                    /*System.out.print("Movimiento Verticalmente a la IZQUIERDA");*/
                }
                if (movimineto == 2){
                    movimineto = 0;//con esto reiniciaremos nuestro movimiento;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        start();//ESTO ES PARA INICIAR EL SENSOR
    }

    private void start(){
        sensorManager.registerListener(sensorEventListener,sensor,sensorManager.SENSOR_DELAY_NORMAL);//ESTO ES PARA INICIAR EL SENSOR
    }

    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause(){
        stop();
        super.onPause();
    }

    @Override
    protected void onResume(){
        start();
        super.onResume();
    }
}
