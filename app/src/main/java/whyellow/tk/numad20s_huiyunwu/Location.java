package whyellow.tk.numad20s_huiyunwu;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Location extends AppCompatActivity {

	LocationManager locationManager;
	Context mContext;
	public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
	TextView latitude ;
	TextView longitude ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		mContext = this;

		latitude = findViewById(R.id.latitude);
		longitude = findViewById(R.id.longitude);


		if(ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// ask permissions here using below code

			Toast.makeText(this, "No Location Permission", Toast.LENGTH_LONG).show();

			latitude.setText("No Permission");
			longitude.setText("No Permission");

			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
					MY_PERMISSIONS_REQUEST_LOCATION);
		}
		locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,
				0, locationListenerGPS);

	}

	LocationListener locationListenerGPS = new LocationListener() {
		@Override
		public void onLocationChanged(android.location.Location location) {
			double latitude_ = location.getLatitude();
			double longitude_ = location.getLongitude();
			latitude.setText(String.valueOf(latitude_));
			longitude.setText(String.valueOf(longitude_));

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}
	};

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_LOCATION: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

					if (ContextCompat.checkSelfPermission(this,
							Manifest.permission.ACCESS_FINE_LOCATION)
							== PackageManager.PERMISSION_GRANTED) {

						//Request location updates:
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,locationListenerGPS );
					}

				}else{
					Toast.makeText(this, "No Location Permission", Toast.LENGTH_LONG).show();

					latitude.setText("No Permission");
					longitude.setText("No Permission");

					ActivityCompat.requestPermissions(this,
							new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
							MY_PERMISSIONS_REQUEST_LOCATION);
				}
				return;
			}

		}

}
}



