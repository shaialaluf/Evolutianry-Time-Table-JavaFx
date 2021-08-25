package Algorithm.FinishConditions;

public class FinishByMinutes  implements FinishCondition {
    private float minutesToStop;
    private float currentTime;

    public FinishByMinutes(float minutesToStop) {
        this.minutesToStop = minutesToStop;
    }

    public float getMinutesToStop() {
        return minutesToStop;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    @Override
    public boolean isFinish(float currentMinutes) {
        this.currentTime=currentMinutes;
        return (currentMinutes>=minutesToStop);
    }

}
