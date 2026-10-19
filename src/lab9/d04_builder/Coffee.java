package lab9.d04_builder;

/**
 * Produsul: imutabil (câmpuri final), constructor privat, creat doar prin Builder.
 */
public final class Coffee {
    private final String size;
    private final int shots;
    private final int sugar;
    private final int milk;
    private final boolean iced;
    private final boolean extraHot;

    private Coffee(Builder b) {
        this.size = b.size;
        this.shots = b.shots;
        this.sugar = b.sugar;
        this.milk = b.milk;
        this.iced = b.iced;
        this.extraHot = b.extraHot;
    }

    public String getSize() { return size; }
    public int getShots() { return shots; }
    public int getSugar() { return sugar; }
    public int getMilk() { return milk; }
    public boolean isIced() { return iced; }
    public boolean isExtraHot() { return extraHot; }

    /** Un builder pre-completat cu valorile curente: "copie cu o modificare". */
    public Builder toBuilder() {
        Builder b = new Builder(size, shots);
        b.sugar = sugar;
        b.milk = milk;
        b.iced = iced;
        b.extraHot = extraHot;
        return b;
    }

    @Override
    public String toString() {
        return "Coffee{" + size + ", shots=" + shots + ", sugar=" + sugar + ", milk=" + milk
                + (iced ? ", iced" : "") + (extraHot ? ", extraHot" : "") + "}";
    }

    public static class Builder {
        // obligatorii: în constructorul builder-ului
        private final String size;
        private final int shots;
        // opționale: cu valori implicite
        private int sugar = 0;
        private int milk = 0;
        private boolean iced = false;
        private boolean extraHot = false;

        public Builder(String size, int shots) {
            this.size = size;
            this.shots = shots;
        }

        public Builder sugar(int sugar) {
            if (sugar < 0) throw new IllegalArgumentException("sugar must not be negative: " + sugar);
            this.sugar = sugar;
            return this;
        }

        public Builder milk(int milk) {
            if (milk < 0) throw new IllegalArgumentException("milk must not be negative: " + milk);
            this.milk = milk;
            return this;
        }

        public Builder iced() {
            this.iced = true;
            return this;
        }

        public Builder extraHot() {
            this.extraHot = true;
            return this;
        }

        public Coffee build() {
            // validarea combinației finale: aici IllegalStateException
            if (iced && extraHot) throw new IllegalStateException("a coffee cannot be both iced and extra hot");
            if (shots < 1) throw new IllegalStateException("at least one shot");
            return new Coffee(this);
        }
    }
}
