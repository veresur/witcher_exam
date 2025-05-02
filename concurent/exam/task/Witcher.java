package concurent.exam.task;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A witcher is a person who makes his fortune by taking up contracts and completing them for gold
 * Has a name and keeps track of his gold
 */
public class Witcher {

    private static final int CONTRACT_FINISH_TIME_MIN = 200;
    private static final int CONTRACT_FINISH_TIME_MAX = 300;

    private final String name;
    // TODO (Part 1) crate gold attribute, 0 is default value
    private final AtomicInteger gold = new AtomicInteger(0);
    private Contract ownedContract;

    public Witcher(final String name){
        this.name = name;
    }

    /**
     * Used in the Simulation's startWitcher method
     * This is how a witcher takes a contract and completes it
     * @param contract The contract being taken and completed
     * @throws InterruptedException handle this in Simulation's startWitcher method
     */
    public void takeContract(Contract contract) throws InterruptedException {
        contract.take();
        this.ownedContract = contract;
        System.out.println(this.name + " has taken " + contract.getInfo());
        System.out.println(this.name + " is travelling for " + contract.getTravelTime() + " msec");
        // TODO (Part 1) wait contract.getTravelTime()
        Thread.sleep(contract.getTravelTime());
        int contractFinishTime = calculateContractTime(contract);
        System.out.println(this.name + " arrived, contract takes " + contractFinishTime + " msec");
        // TODO (Part 1) wait contractFinishTime
        Thread.sleep(contractFinishTime);
        addGold(contract.getReward());
        this.ownedContract = null;
        System.out.println(this.name + " finished " + contract.getInfo() + ", now has " + getGold() + " gold");

    }

    public int calculateContractTime(Contract contract){
        int difficultyMultiplier = contract.getDifficulty().ordinal() + 1;
        int min = CONTRACT_FINISH_TIME_MIN * difficultyMultiplier;
        int max = CONTRACT_FINISH_TIME_MAX * difficultyMultiplier;
        return getRandom(min, max);
    }

    public String getName(){
        return this.name;
    }

    /**
     * Used in Simulation's checkSimulationOver in part 2
     * @return The amount of gold the witcher has
     */
    public int getGold(){
        // TODO (Part 1) return gold
        return gold.get();
    }

    private void addGold(int amount){
        // TODO (Part 1) add amount to gold
        gold.addAndGet(amount);
    }

    private int getRandom(int min, int max){
        // TODO (Part 1) random generation
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
