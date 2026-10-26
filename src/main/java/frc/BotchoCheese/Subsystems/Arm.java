/* Dave the debugging duck
                                            ██████████                                  
                                      ░░  ██░░░░░░░░░░██                                
                                        ██░░░░░░░░░░░░░░██                              
                                        ██░░░░░░░░████░░██████████                      
                            ██          ██░░░░░░░░████░░██▒▒▒▒▒▒██                      
                          ██░░██        ██░░░░░░░░░░░░░░██▒▒▒▒▒▒██                      
                          ██░░░░██      ██░░░░░░░░░░░░░░████████                        
                        ██░░░░░░░░██      ██░░░░░░░░░░░░██                              
                        ██░░░░░░░░████████████░░░░░░░░██                                
                        ██░░░░░░░░██░░░░░░░░░░░░░░░░░░░░██                              
                        ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██                            
                        ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██                            
                        ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██                            
                        ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██                            
                        ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██                            
                        ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██                              
                          ██░░░░░░░░░░░░░░░░░░░░░░░░░░██                                
                            ██████░░░░░░░░░░░░░░░░████                                  
                                  ████████████████                                                                                                                 
*/
package frc.BotchoCheese.Subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.BotchoCheese.Constants.RobotMap;

public class Arm extends SubsystemBase {
    private double extendPercent = 0.5;
    private double retractPercent = -0.5;
    // TODO: Add a TalonFX motor and DutyCycleOut. (DONE)
    private final TalonFX armMotor = new TalonFX(RobotMap.ARM_MOTOR_ID);
    private final DutyCycleOut armRequest = new DutyCycleOut(0);
        public Arm() {
            
            // This configures the motor with specific limits for safety purposes.
            TalonFXConfiguration config = new TalonFXConfiguration();
            
            config.CurrentLimits.StatorCurrentLimit = 60;
            config.CurrentLimits.StatorCurrentLimitEnable = true;
            config.CurrentLimits.SupplyCurrentLimit = 40;
            config.CurrentLimits.SupplyCurrentLimitEnable = true;
            
            config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

            // TODO: Replace motorName with the name of the motor you just made.(DONE)
            armMotor.getConfigurator().apply(config);
        }

    // TODO: Add Commands that allows the arm to extend (forward) or retract (backward). (DONE)
    public void setPercent(double percent) {
        armMotor.setControl(armRequest.withOutput(percent));
    }
    public void extend() {
        setPercent(extendPercent);   // forward/outward
    }
    public void retract() {
        setPercent(retractPercent);   // forward/outward
    }
    public void stop() {
        setPercent(0);
    }
}
