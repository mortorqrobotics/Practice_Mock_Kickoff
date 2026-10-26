package frc.BotchoCheese.Subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.BotchoCheese.Constants.RobotMap;

public class Grapple extends SubsystemBase {
    
    // TalonFX motor and DutyCycleOut control request
    private final TalonFX m_motor = new TalonFX(RobotMap.GRAPPLE_MOTOR_ID);
    private final DutyCycleOut m_dutyCycle = new DutyCycleOut(0);

    public Grapple() {
        // This configures the motor with specific limits for safety purposes.
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.CurrentLimits.StatorCurrentLimit = 60;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = 40;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        // Apply configuration to the motor
        m_motor.getConfigurator().apply(config);
    }

    /**
     * Creates a command to run the grapple at a specified speed.
     * @param speed Duty cycle output from -1.0 to 1.0
     */
    public Command runGrappleCommand(double speed) {
        return this.runEnd(
            () -> m_motor.setControl(m_dutyCycle.withOutput(speed)),
            () -> m_motor.setControl(m_dutyCycle.withOutput(0))
        );
    }

    /**
     * Command to move the grapple up.
     */
    public Command moveUpCommand() {
        return runGrappleCommand(0.5);
    }

    /**
     * Command to move the grapple down.
     */
    public Command moveDownCommand() {
        return runGrappleCommand(-0.5);
    }
}