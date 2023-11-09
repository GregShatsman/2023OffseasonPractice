// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ArmSubsystem extends SubsystemBase {

  /** State variables */
  private boolean isActive = false;
  private boolean isSensingTarget = false;

  /** Simulation related variables */
  private long simNextEventTime = 0;



  /** Creates a new ArmSubsystem. */
  public ArmSubsystem() {
    CANSparkMax bottomMotor = new CANSparkMax(6, MotorType.kBrushless);
    CANSparkMax topMotor = new CANSparkMax(7, MotorType.kBrushless);


    private void setMotorSpeed(double speed) {
        bottomMotor.set(speed);
        topMotor.set(speed);
    }

    public void setBaseMotorSpeed(double speed) {
        bottomMotor.set(speed);
    }

    public void setElbowSpeed(double speed) {
        topMotor.set(speed);
    }

    

   
  }

  /**
   * 
   * @return
   */
  public void ArmDoSomething() {
    if (isActive) {
      // Do something

    }
  }

  /**
   * Arm command factory method.
   *
   * @return a command
   */
  public CommandBase ArmMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * Builds a method that activates the subsystem
   *
   * @return a command
   */
  public CommandBase ArmActivateCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          System.out.println("Arm Activated");
          isActive = true;
        });
  }

  /**
   * Builds a method that deactivates the subsystem
   *
   * @return a command
   */
  public CommandBase ArmDeactivateCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          System.out.println("Arm Deactivated");
          isActive = false;
        });
  }

  /**
   * An Arm method querying a boolean state of the subsystem (for Arm, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean ArmCondition() {
    // Query some boolean state, such as a digital sensor.
    return isSensingTarget;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * Toggle whether the subsystem is sensing a target
   */
  private void simEvent() {
    // Toggle the sensing state
    isSensingTarget = !isSensingTarget;
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    long now = System.currentTimeMillis();

    if (now >= simNextEventTime) {
      simEvent();
      simNextEventTime = now + (1000 * 2);
    }
  }
}
