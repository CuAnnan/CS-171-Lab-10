import java.util.Random;
import java.util.Scanner;

/**
 * THis is just a simple structure to hold information about the result of an attack to make returning and handling it easier.
 * Setters with return of this to make chaining possible. This shouldn't need any further commenting as it's just a POJO with
 * public values and only setters.
 */
class WeaponAttack
{
    /**
     * The result of the attack roll
     */
    public int attackRoll;
    /**
     * The pre-buff pre-crit damage
     */
    public int rawDamage;
    /**
     * If the attack was a crit
     */
    public boolean crit;
    /**
     * If the attack was a miss or hit
     */
    public boolean hit;
    /**
     * The calculated net damage
     */
    public int netDamage;
    /**
     * The amount of buff applied
     */
    public int buffAmount;
    
    public WeaponAttack(int attackRoll, boolean hit)
    {
        this.attackRoll = attackRoll;
        this.hit = hit;
        this.crit = false;
        this.rawDamage = 0;
        this.netDamage = 0;
        this.buffAmount = 0;
    }

    public WeaponAttack setRawDamage(int rawDamage)
    {
        this.rawDamage = rawDamage;
        this.calculateNetDamage();
        return this;
    }
    
    public WeaponAttack setCrit(boolean crit)
    {
        this.crit = crit;
        this.calculateNetDamage();
        return this;
    }

    public WeaponAttack setBuffAmount(int buffAmount)
    {
        this.buffAmount = buffAmount;
        this.calculateNetDamage();
        return this;
    }

    public void calculateNetDamage()
    {
        this.netDamage = this.rawDamage + this.buffAmount;
        this.netDamage *= this.crit?2:1;
    }


    public WeaponAttack setAttackRoll(int attackRoll)
    {
        this.attackRoll = attackRoll;
        return this;
    }
    

}

/**
 * A class to encapsulate the logic for a weapon, also serves to encapsulate the logic for a player.
 * In a slightly more serious situation, I'd abstract out player to a separate class, to make accounting for multiple weapons or different armor etc.
 */
class Weapon
{
    /**
     * The name of the weapon
     */
    protected String name;
    /**
     * The number and values of each dice to roll
     */
    protected int[] dice;
    /**
     * the weapon's intrinsic bonus value
     */
    protected int bonus;
    /**
     * The number to roll equal to or better than to get double damage
     */
    protected int critThreshold;
    /**
     * The result of a successful attack
     */
    protected WeaponAttack lastAttack;
    

    /**
     * The PRNG.
     */
     protected Random rng;
    /**
     * The attack die
     */
    protected int attackDie = 20;
    /**
     * Text representing the standard dnd damage format (ndn [+ mnm [...+pdp]])
     */
    protected String damageText;
    /**
     * Text representing the damage range in the <min> - <max> format
     */
    private String damageRange;
    
    /**
     * Default constructor, creates a weapon with the same base stats as the Q4 method
     */
    public Weapon()
    {
        this("Zweihaander", new int[]{8,8});
    }

    /**
     * A constructor which allows you to specify name and damage dice
     * @param name
     * @param dice
     */
    public Weapon(String name, int[] dice)
    {
        this(name, dice, 0);
    }

    public Weapon(String name, int[] dice, int bonus)
    {
        this(name, dice, bonus, 20);
    }

    /**
     * A constructor which allows you to specify name and damage dice and damage bonus
     * @param name
     * @param dice
     * @param bonus
     */
    public Weapon(String name, int[] dice, int bonus, int critThreshold)
    {
        this.name = name;
        this.dice = dice;
        this.bonus = bonus;
        this.critThreshold = critThreshold;
        this.rng = new Random();
        this.buildDamageInformation();
    }

    /**
     * Try to attack an enemy
     * @param enemy The enemy to attack
     * @param buff  The value of buffs added to the attack
     * @return      Returns true on a hit, false on amiss1
     */
    public void attack(Enemy enemy, int buff)
    {
        int attackRoll = this.rng.nextInt(this.attackDie) + 1;
        boolean attackResult = attackRoll > enemy.getArmor();
        
        lastAttack = new WeaponAttack(attackRoll, attackResult);
        if(attackResult)
        {
            this.rollDamage(buff, attackRoll == this.critThreshold);
            enemy.damage(this.lastAttack.netDamage);
        }
    }

    /**
     * A method to roll the damage of the weapon
     * @param buff  The value of any buffs applied to this roll
     * @return      The result of the damage roll
     */
    public void rollDamage(int buff, boolean isCrit)
    {
        int damage = this.bonus;
        for(int die: this.dice)
        {
            damage += this.rng.nextInt(die) + 1;
        }
        this.lastAttack
            .setRawDamage(damage)
            .setBuffAmount(buff)
            .setCrit(isCrit);
    }

    public String getDamageText()
    {
        return this.damageText;
    }

    public String getDamageRange()
    {
        return this.damageRange;
    }

    /**
     * A method to "add" a value "to" an array. In reality copies the old array to a new array of one greater size and adds the value to the end
     * @param oldArray      The array to "add to"
     * @param valueToAdd    The value to add
     * @return              The newly created array
     */
    public int[] arrayAdd(int[] oldArray, int valueToAdd)
    {
        int[] newArray = new int[oldArray.length + 1];
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        newArray[oldArray.length] = valueToAdd;
        return newArray;
    }

    /**
     * A method to get the number and type of each dice in the damage of the weapon.
     */
    public void buildDamageInformation()
    {
        // nearly a hash map.
        int diceTypes[] = new int[0];
        int diceCounts[] = new int[0];

        
        for(int die: this.dice)
        {
            int diceIndex = -1;
            int searchIndex = 0;
            while(searchIndex < diceTypes.length && diceIndex == -1)
            {
                if(die == diceTypes[searchIndex])
                {
                    diceIndex = searchIndex;
                }
                searchIndex++;
            }
            if(diceIndex == -1)
            {
                diceTypes = arrayAdd(diceTypes, die);
                diceCounts = arrayAdd(diceCounts, 1);
            }
            else
            {
                diceCounts[diceIndex]++;
            }
        }

        String[] damageParts = new String[diceTypes.length];
        int max= 0;
        int min = 0;
        for(int i = 0; i < diceTypes.length; i++)
        {
            damageParts[i] = String.format("%dd%d", diceCounts[i], diceTypes[i]);
            max += diceCounts[i] * diceTypes[i];
            min += diceCounts[i];
        }
        this.damageRange = String.format("%d - %d", min, max);
        this.damageText =  String.join(" + ", damageParts);
    }
}

/**
 * A class to encapsulate the enemy
 */
class Enemy
{
    protected String name;
    protected int hp;
    protected int armor;
    
    public Enemy()
    {
        this("Troll", 100, 12);
    }

    /**
     * Actual default constructor
     * @param name  The name of the enemy
     * @param hp    The amount of hp it has
     * @param armor The armor value
     */
    public Enemy(String name, int hp, int armor)
    {
        this.name = name;
        this.hp = hp;
        this.armor = armor;
    }

    public int getArmor()
    {
        return this.armor;
    }

    public int getHP()
    {
        return this.hp;
    }

    /**
     * A method to reduce the enemy's health
     * @param damageAmount
     */
    public void damage(int damageAmount)
    {
        this.hp -= damageAmount;
        this.hp = Math.max(0, this.hp);
    }
}

/**
 * A class to hold the logic of the attack rounds.
 */
public class QuestionFour
{
    protected static int buffAmount = 5;
    
    public static void main(String[] args)
    {
        
        Scanner sc = new Scanner(System.in);
        Weapon playerWeapon = new Weapon();
        Enemy troll = new Enemy();
        int turns = 0;


        System.out.println("Let's play a game. Type \"A\" to attack, \"B\" to buff your next attack. Kill the enemy to win!");
        System.out.println(String.format("You must roll higher than the enemy armor class (%d) to hit. Roll 20 for a critical hit!", troll.getArmor()));
        System.out.println(String.format("Your damage is %s (%s)", playerWeapon.getDamageRange(), playerWeapon.getDamageText()));
        
        int buff = 0;
        // the business logic
        while(troll.getHP() > 0)
        {
            String act = sc.nextLine().toLowerCase();
            
            switch(act)
            {
                case "a":
                    playerWeapon.attack(troll, buff);
                    printRoundResult(playerWeapon, troll);
                    buff = 0;
                    break;
                case "b":
                    buff = buffAmount;
                    break;
                default:
                    System.out.println("Invalid input.\nType \"A\" to attack, \"B\" to buff your next attack. Kill the enemy to win!");
                    break;
            }
            turns++;
        }
        System.out.println(String.format("You defeated the %s in %d turns", troll.name, turns));
        sc.close();
    }

    /**
     * A method to print out the result of the round
     * @param weapon    The weapon used in the attack
     * @param enemy     The enemy attacked
     */
    public static void printRoundResult(Weapon weapon, Enemy enemy)
    {
        WeaponAttack attack = weapon.lastAttack;
        System.out.println("You rolled "+attack.attackRoll);
        if(attack.hit)
        {
            System.out.print(String.format("You %shit the %s with your %s", attack.crit?"critically ":"", enemy.name, weapon.name));
            System.out.println(attack.buffAmount > 0 ? " (buffed attack).":".");
            System.out.println(String.format("%s took %d damage and has %d HP remaining", enemy.name, attack.netDamage, enemy.getHP()));
        }
        else
        {
            System.out.println(String.format("You missed the %s", enemy.name));
        }
    }
}