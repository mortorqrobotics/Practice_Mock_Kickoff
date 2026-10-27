package frc.BotchoCheese.Subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.BotchoCheese.Constants.RobotMap;

public class Arm extends SubsystemBase {
    // TODO: Add a TalonFX motor and DutyCycleOut.
    public Arm() {

        // This configures the motor with specific limits for safety purposes.
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.CurrentLimits.StatorCurrentLimit = 60;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = 40;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        // TODO: Replace motorName with the name of the motor you just made.
        motorName.getConfigurator().apply(config);
    }

    // TODO: Add Commands that allows the arm to extend (forward) or retract (backward).
}