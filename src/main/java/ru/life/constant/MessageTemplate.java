package ru.life.constant;

public class MessageTemplate {
    public static final String HELP_MESSAGE = "Rules:\n" +
            "\n" +
            "1) The place of action of the game is a boundless plane marked into cells.\n\n" +
            "2) Each cell on this surface has eight neighbors surrounding it, and can be in two states: be \"alive\" (filled) or \"dead\" (empty).\n\n" +
            "3) The distribution of cells at the beginning of the game is called the first generation. The emergence of the next generation is calculated according to this rules:\n\n" +
            "    3.1) in an empty (dead) cell, with which three living cells are adjacent, life is born;\n\n" +
            "    3.2) if a living cell has two or three living neighbors, then this cell continues to live; otherwise (if there are less than two or more than three living neighbors), the cell dies (“from loneliness” or “from overcrowding”).\n\n" +
            "4) The game ends if:\n\n" +
            "    4.1) not a single \"living\" cell will remain on the field;\n\n" +
            "    4.2) the configuration at the next step will exactly (without shifts and rotations) repeat itself at one of the earlier steps (a periodic configuration is added)\n\n" +
            "    4.3) at the next step, none of the cells changes its state (the previous rule appears one step back, a stable configuration is formed)\n\n" +
            "\n" +
            "How to start:\n" +
            "Place the initial configuration of \"live\" cells. Next, press the spacebar to start the game. \n" +
            "To add new cells, press the spacebar.\n\n";

    public static final String MAX_SPEED = "The maximum speed is already set. Speed up is not available.";
    public static final String MIN_SPEED = "The minimum speed is already set. Speed down is not available.";
    public static final String SAVED_SUCCESS = "Screen captured successfully.";
    public static final String SAVING_FAILED = "Screen capturing was failed. Please check chosen directory and file name.";
    public static final String SAVING_TITLE = "Saving";
}
