package ru.javaops.basejava;

public class TestDeadLock {
    public static void main(String[] args) {
        Acc acc_current = new Acc("current", 10_000F);
        Acc acc_deposit = new Acc("deposit", 20_000F);

        Thread thread0 = new Thread(() -> acc_current.transaction(acc_deposit, 1F));
        Thread thread1 = new Thread(() -> acc_deposit.transaction(acc_current, 2F));
        thread0.start();
        thread1.start();
    }

    static class Acc {
        String accName;
        float accSum;

        private Acc(String accName, float accSum) {
            this.accName = accName;
            this.accSum = accSum;
        }

        private void transaction(Acc target, float sum) {
            synchronized (this) {
                if (this.accSum >= sum & sum > 0) {
                    this.accSum -= sum;
                } else {
                    System.out.println("Invalid amount");
                    return;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (target) {
                    target.accSum += sum;
                }
            }
        }
    }
}
