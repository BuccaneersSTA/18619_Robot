package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Gyro;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class TankDrive extends CommandBase {

    private final DriveTrain m_DriveTrain;

    private final Gyro gyro;

    private final DoubleSupplier leftY, leftX, rightX;

    private final BooleanSupplier reset;

    private final Telemetry telemetry;

    static boolean state = false;

    public TankDrive(DriveTrain m_DriveTrain,
                     Gyro gyro, DoubleSupplier leftY, DoubleSupplier leftX, DoubleSupplier rightX,
                     BooleanSupplier reset,
                     Telemetry telemetry) {
        this.m_DriveTrain = m_DriveTrain;
        this.gyro = gyro;

        this.leftY = leftY;
        this.leftX = leftX;
        this.rightX = rightX;

        this.reset = reset;

        this.telemetry = telemetry;

        addRequirements(m_DriveTrain, gyro);
    }

    @Override
    public void execute() {



        if(reset.getAsBoolean()){
            gyro.resetAngle();
        }

        if(state){
            m_DriveTrain.driveFieldCentric(leftX.getAsDouble(), leftY.getAsDouble(), rightX.getAsDouble(), gyro.getAngle());

        }else{
            m_DriveTrain.driveRobotCentric(leftX.getAsDouble(), leftY.getAsDouble(), rightX.getAsDouble());
        }
        if(state){
            telemetry.addLine()
                    .addData("Field Centric", "");
        }else{
            telemetry.addLine()
                    .addData("Driver Centric", "");
        }

        telemetry.update();
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interuppted){
        m_DriveTrain.stop();
    }

}
