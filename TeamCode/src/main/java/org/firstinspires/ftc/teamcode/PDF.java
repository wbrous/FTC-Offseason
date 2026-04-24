package org.firstinspires.ftc.teamcode;

public class PDF {
    double kP, kD, kF;
    double lastError;

    public PDF(double kP, double kD, double kF) {
        this.kP = kP;
        this.kD = kD;
        this.kF = kF;
    }

    public double calc(double desired, double current) {
        double error = desired - current;
        double P = error * kP;
        double D = (error - lastError) * kD;
        double F = desired * kF;

        lastError = error;
        return P+D+F;
    }
}
