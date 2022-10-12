**Rules:**

1) The place of action of the game is a boundless plane marked into cells.
2) Each cell on this surface has eight neighbors surrounding it, and can be in two states: be "alive" (filled) or "dead" (empty).
3) The distribution of cells at the beginning of the game is called the first generation. The emergence of the next generation is calculated according to this rules:
    3.1) in an empty (dead) cell, with which three living cells are adjacent, life is born;
    3.2) if a living cell has two or three living neighbors, then this cell continues to live; otherwise (if there are less than two or more than three living neighbors), the cell dies (“from loneliness” or “from overcrowding”).
4) The game ends if:\n\n" +
    4.1) not a single \"living\" cell will remain on the field;\n\n" +
    4.2) the configuration at the next step will exactly (without shifts and rotations) repeat itself at one of the earlier steps (a periodic configuration is added)
    4.3) at the next step, none of the cells changes its state (the previous rule appears one step back, a stable configuration is formed)

   
![giff](https://user-images.githubusercontent.com/61387671/194599158-394b8997-c6fc-4094-8612-e3ce0ac9b9e8.gif)

![scr](https://user-images.githubusercontent.com/61387671/194599180-93e68b4b-ccb8-4c5e-bce3-e5bd3725a78d.png)

