package al.alec.custommachinerybotania.client.integration.jei.mana;

public class Mana {
    private final int amount;
    private final boolean isPerTick;

    public Mana(int amount, boolean isPerTick) {
        this.amount = amount;
        this.isPerTick = isPerTick;
    }

    public int getAmount() {
        return this.amount;
    }

    public boolean isPerTick() {
        return this.isPerTick;
    }
}
