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

  CANSparkMax bottomMotor = new CANSparkMax(7, MotorType.kBrushless);
  CANSparkMax bottomMotor2 = new CANSparkMax(8, MotorType.kBrushless);

  CANSparkMax topMotor = new CANSparkMax(15, MotorType.kBrushless);



  /** Creates a new ArmSubsystem. */
  public ArmSubsystem() {
    bottomMotor.restoreFactoryDefaults();
    bottomMotor.setSmartCurrentLimit(50);
    bottomMotor.setIdleMode(IdleMode.kBrake);
    bottomMotor.setOpenLoopRampRate(0.2);
    bottomMotor.setInverted(false);

    bottomMotor2.restoreFactoryDefaults();
    bottomMotor2.setSmartCurrentLimit(50);
    bottomMotor2.setIdleMode(IdleMode.kBrake);
    bottomMotor2.setOpenLoopRampRate(0.2);
    bottomMotor2.setInverted(false);

    bottomMotor2.follow(bottomMotor);


  }

  private void setMotorSpeed(double speed) {
      bottomMotor.set(speed);
      topMotor.set(speed);
  }

  public void baseArmAttack(double speed) {
      System.out.println("I am chopping!");

      bottomMotor.set(speed);

  }

  public Command baseArmSmashSequence() {
    return Commands.sequence(
      baseArmAttack(.2).withTimeout(0.5)
      .andThen(Commands.waitSeconds(1))
      .andThen(baseArmAttack(-0.2).withTimeout(0.5)
      .andThen(setMotorSpeed(0)))
      );
  }

  public void setElbowSpeed(double speed) {
      topMotor.set(speed);
  }

  /**
   * 
   * @return

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
          baseArmSmashSequence();
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
