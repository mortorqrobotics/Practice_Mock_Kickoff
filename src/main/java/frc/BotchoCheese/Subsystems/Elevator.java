package frc.BotchoCheese.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.BotchoCheese.Constants.RobotMap;

public class Elevator extends SubsystemBase {
    private final TalonFX motor = new TalonFX(RobotMap.ELEVATOR_MOTOR);
    private final DutyCycleOut dutyCycle = new DutyCycleOut(0);
    private final DigitalInput bottomLimit = new DigitalInput(RobotMap.ELEVATOR_BOTTOM_LIMIT_DIO);

    public Elevator() {
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.CurrentLimits.StatorCurrentLimit = RobotMap.ELEVATOR_STATOR_LIMIT;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = RobotMap.ELEVATOR_SUPPLY_LIMIT;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        config.Voltage.PeakForwardVoltage = RobotMap.ELEVATOR_PEAK_VOLTAGE;
        config.Voltage.PeakReverseVoltage = -RobotMap.ELEVATOR_PEAK_VOLTAGE;

        config.OpenLoopRamps.DutyCycleOpenLoopRamp = RobotMap.ELEVATOR_RAMP_SECONDS;

        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = RobotMap.ELEVATOR_MAX_ROTATIONS;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = RobotMap.ELEVATOR_MIN_ROTATIONS;

        motor.getConfigurator().apply(config);
        motor.setPosition(0);
    }

    public boolean isAtBottom() {
        return bottomLimit.get() != RobotMap.ELEVATOR_BOTTOM_LIMIT_INVERTED;
    }

    public boolean isAtTop() {
        return getPosition() >= RobotMap.ELEVATOR_MAX_ROTATIONS;
    }

    public double getPosition() {
        return motor.getPosition().getValueAsDouble();
    }

    private void setOutput(double output) {
        if (output < 0 && isAtBottom()) {
            output = 0;
        }
        if (output > 0 && isAtTop()) {
            output = 0;
        }
        motor.setControl(dutyCycle.withOutput(output));
    }

    public void moveUp() {
        setOutput(RobotMap.ELEVATOR_UP_SPEED);
    }

    public void moveDown() {
        setOutput(RobotMap.ELEVATOR_DOWN_SPEED);
    }

    public void stop() {
        motor.setControl(dutyCycle.withOutput(0));
    }

    public Command moveUpCommand() {
        return runEnd(this::moveUp, this::stop);
    }

    public Command moveDownCommand() {
        return runEnd(this::moveDown, this::stop);
    }

    public Command stopCommand() {
        return runOnce(this::stop);
    }

    @Override
    public void periodic() {
        if (isAtBottom()) {
            motor.setPosition(RobotMap.ELEVATOR_MIN_ROTATIONS);
        }

        SmartDashboard.putNumber("Elevator/Position", getPosition());
        SmartDashboard.putBoolean("Elevator/AtBottom", isAtBottom());
        SmartDashboard.putBoolean("Elevator/AtTop", isAtTop());
    }
}