
package com.example.fimgerprintauthentication;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.biometric.BiometricFragment;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ImageView img = findViewById(R.id.image);
        TextView text = findViewById(R.id.info_text);
        
        
        // check device for Biometric and enable or disable the imageview or button based on switch case 
        
        BiometricManager biometricmanager = BiometricManager.from(this);
        switch(biometricmanager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG|BiometricManager.Authenticators.DEVICE_CREDENTIAL)){
            
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
            Toast.makeText(getApplicationContext(),"Harddware Unavailable",Toast.LENGTH_LONG).show();
            img.setEnabled(false);
            break;
            
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
            Toast.makeText(getApplication(),"Biometrics not enrolled",Toast.LENGTH_LONG).show();
            img.setEnabled(false);
            break;
            
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
            Toast.makeText(getApplicationContext(),"no hardware found",Toast.LENGTH_LONG).show();
            img.setEnabled(false);
            break;
            
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
            Toast.makeText(getApplicationContext(),"biometrics required seccurity updated from device manufacturer",Toast.LENGTH_LONG).show();
            img.setEnabled(false);
            break;
            
            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
            Toast.makeText(getApplicationContext(),"Biometric which you enrolled is not support for this app",Toast.LENGTH_LONG).show();
            img.setEnabled(false);
            break;
            
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
            Toast.makeText(getApplicationContext(),"satus unknown",Toast.LENGTH_LONG).show();
            img.setEnabled(false);
            break;
            
            case BiometricManager.BIOMETRIC_SUCCESS:
            Toast.makeText(getApplicationContext(),"Biometric Process is Available",Toast.LENGTH_LONG).show();
            img.setEnabled(true);
            break;
            }
            
            //Once the device has valid biometrics we can move forward from her
            
            executor = ContextCompat.getMainExecutor(this);
            //prompt result accessed and moving to another activity is created here
            
            biometricPrompt = new BiometricPrompt(this,executor,new BiometricPrompt.AuthenticationCallback(){
                
                
                
                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    // TODO: Implement this method
                    Intent a = new Intent();
                    a.setClass(getApplicationContext(),SuccessActivity.class);
                    startActivity(a);
                    finish();
                }
                
                
                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    // TODO: Implement this method
                }
                
                
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errorDescription) {
                    super.onAuthenticationError(errorCode, errorDescription);
                    // TODO: Implement this method
                String errorMessage;        
                    //Check error code with switch
                    
                    switch(errorCode){
                       
                       case BiometricPrompt.ERROR_CANCELED:
                       errorMessage = "User canceled the Operation";
                       break;
                       
                       case BiometricPrompt.ERROR_HW_NOT_PRESENT:
                       errorMessage = "Biometric Hardware not Present";
                       break;
                     
                    case BiometricPrompt.ERROR_HW_UNAVAILABLE:
                    errorMessage ="Biometric Hardware UnAvailable";
                    break;
                    
                    case BiometricPrompt.ERROR_LOCKOUT:
                    errorMessage = "Biometric Lockout";
                    break;
                    
                    case BiometricPrompt.ERROR_LOCKOUT_PERMANENT:
                    errorMessage = "Biometric Permanently Locked Out";
                    break;
                    
                    case BiometricPrompt.ERROR_NEGATIVE_BUTTON:
                    errorMessage = "Biometric Canceled through Negative Button";
                    break;
                    
                    case BiometricPrompt.ERROR_NO_BIOMETRICS:
                    errorMessage = "Biometrics Not Enrolled";
                    break;
                    
                    case BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL:
                    errorMessage =" No Device Credentials Found";
                    break;
                    
                    case BiometricPrompt.ERROR_NO_SPACE:
                    errorMessage ="No space for Biometrics";
                    break;
                    
                    case BiometricPrompt.ERROR_SECURITY_UPDATE_REQUIRED:
                    errorMessage = "Biometrics needs Security Update to use";
                    break;
                    
                    case BiometricPrompt.ERROR_TIMEOUT:
                    errorMessage = "Biometric Authentication TimeOut";
                    break;
                    
                    case BiometricPrompt.ERROR_UNABLE_TO_PROCESS:
                    errorMessage = "Unable to Process Biometrics";
                    break;
                    
                    case BiometricPrompt.ERROR_VENDOR:
                    errorMessage ="Vendor Specific Biometric Error";
                    break;
                    
                    case BiometricPrompt.ERROR_USER_CANCELED:
                    errorMessage = "User Canceled the OPeration";
                    break;
                    
                    default:
                    errorMessage = "Unknown Error Occurred";
                    break;
                    }
                    Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                    
                }
                
             });
        
        //Biometric prompt Builder
        
        img.setOnClickListener(v->{
            promptInfo = new BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint Authentication")
            .setDescription("This is a Test Version of Fingerprint Authentication")
            .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL|BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build();
            
            biometricPrompt.authenticate(promptInfo);
            
        });
             
        }
}
