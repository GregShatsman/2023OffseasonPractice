// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.math.controller.*;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class ArmSubsystem extends SubsystemBase {

  /** State variables */
  private boolean isActive = false;
  private boolean isSensingTarget = false;

  /** Simulation related variables */
  private long simNextEventTime = 0;

  static CANSparkMax bottomMotor = new CANSparkMax(7, MotorType.kBrushless);
  CANSparkMax bottomMotor2 = new CANSparkMax(8, MotorType.kBrushless);

  static CANSparkMax topMotor = new CANSparkMax(15, MotorType.kBrushless);
  CANSparkMax topMotor2 = new CANSparkMax(17, MotorType.kBrushless);

  static DutyCycleEncoder topMotorEncoder = new DutyCycleEncoder(0);

  /** Creates a new ArmSubsystem. */
  public ArmSubsystem() {
    bottomMotor.restoreFactoryDefaults();
    bottomMotor.setSmartCurrentLimit(50);
    bottomMotor.setOpenLoopRampRate(0.2);
    bottomMotor.setInverted(false);

    bottomMotor2.restoreFactoryDefaults();
    bottomMotor2.setSmartCurrentLimit(50);
    bottomMotor2.setOpenLoopRampRate(0.2);
    bottomMotor2.setInverted(false);

    bottomMotor2.follow(bottomMotor);

    topMotor2.restoreFactoryDefaults();
    topMotor2.setSmartCurrentLimit(50);
    topMotor2.setOpenLoopRampRate(0.2);
    topMotor2.setInverted(false);

    topMotor.restoreFactoryDefaults();
    topMotor.setSmartCurrentLimit(50);
    topMotor.setOpenLoopRampRate(0.2);
    topMotor.setInverted(false);

    topMotor2.follow(topMotor);
  }

  public static Command baseArmAttack(double speed) {
    
      return new FunctionalCommand(null, () -> {
        topMotor.set(speed);
      }, (bool) -> {}, () -> {return false;});
  }

  public static Command baseArmSmashSequence() {
    PIDController nirmit = new PIDController(4, 1, 0);
    PIDController prashanth = new PIDController(4, 1, 0);

    return Commands.sequence(
      baseArmAttack(.29).withTimeout(1)
      .andThen(Commands.waitSeconds(1))
      .andThen(baseArmAttack(-0.4).withTimeout(0.3)
      .andThen(baseArmAttack(0.2).withTimeout(.5))
      )
      );
  }

  public void setElbowSpeed(double speed) {
      topMotor.set(speed);
  }

  public static void printEncoder()
 {
  System.out.println(topMotorEncoder.getDistance());
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
  public Command ArmActivateCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    // return runOnce(
    //     () -> {
    //       System.out.println("Arm Activated");
    //       baseArmSmashSequence();
    //       isActive = true;
    //     });
    return baseArmSmashSequence();
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
