package leema.com.daytrip1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class BmiCalculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bmi_calculator);

        final ImageButton calcBMI = (ImageButton) findViewById(R.id.calcbutton);
        final EditText userHeight = (EditText) findViewById(R.id.enterheight);
        final EditText userWeight = (EditText) findViewById(R.id.enterweight);
        final double[] heightNumber = new double[1];
        final double[] weightNumber = new double[1];
        final TextView calcResult = (TextView) findViewById(R.id.result_bmi);

        ImageButton bmiBack = findViewById(R.id.bmi_back);

        bmiBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackBmi = new Intent(BmiCalculator.this, FullscreenActivity.class);
                startActivity(goBackBmi);
            }
        });



        calcBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String heightString = userHeight.getText().toString();
                heightNumber[0] = Double.parseDouble(heightString);

                String weightString = userWeight.getText().toString();
                weightNumber[0] = Double.parseDouble(weightString);

                double bmiResultNr = calculateBMI(heightNumber[0], weightNumber[0]);
                String bmiResultString = Double.toString(bmiResultNr);
                calcResult.setText(bmiResultString);
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }
    private double calculateBMI(double height, double weight) {
        double bodyMassIndex;
        double heightInCm = height/100;

        bodyMassIndex = weight/(Math.pow(heightInCm, 2));

        return bodyMassIndex;
    }
}
