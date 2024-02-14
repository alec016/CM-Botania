package al.alec.custommachinerybotania.client.integration.jei.mana;

public record Mana(int amount, boolean isPerTick) {

    public int getAmount() {
        return this.amount;
    }

    public boolean isPerTick() {
        return this.isPerTick;
    }
}
