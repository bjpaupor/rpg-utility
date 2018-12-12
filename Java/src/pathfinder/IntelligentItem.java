package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;

import pathfinder.Artifact.Type;
import pathfinder.MagicItem.Activation;
import pathfinder.MagicItem.Slot;

public class IntelligentItem extends Item implements MagicItem{
    private Alignment alignment;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private String[] languages;
    private String[] senses;
    private String communication;
    private String[] powers;
    private String specialPurpose;
    private int ego;
    private MagicItem baseItem;

    public IntelligentItem(String fileName) {
     if (!fileName.endsWith(".intelligentItem")) {
         System.out.println("-> " + fileName + ": Incorrect file type, file must be .intelligentItem\n");
         return;
     }
     else 
         try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
             setName(Tools.readALine(read));
             setDescription(Tools.readALine(read));
             setPrice(Tools.readALine(read));
             setWeight(Tools.readALine(read));
             setMaterial(Tools.readALine(read));
             setHP(Integer.parseInt(Tools.readALine(read)));
             setBreakDC(Integer.parseInt(Tools.readALine(read)));
             School school = School.valueOf(Tools.readALine(read).toUpperCase());
             Descriptor[] descriptors = new Descriptor[Integer.parseInt(Tools.readALine(read))];
             for (int i = 0; i < descriptors.length; i++)
                 descriptors[i] = Descriptor.valueOf(Tools.readALine(read).toUpperCase());
             activation = Activation.valueOf(Tools.readALine(read).toUpperCase());
             slot = Slot.valueOf(Tools.readALine(read).toUpperCase());
             cl = Integer.parseInt(Tools.readALine(read));
             aura = new Aura(school, descriptors, Aura.getItemStrength(cl));
             type = Type.valueOf(Tools.readALine(read).toUpperCase());
             destruction = Tools.readALine(read);
         }
         catch (Exception ex) {
             System.out.println("->" + getName() + ": Failed to interpret feature file!");
             ex.printStackTrace();
             return;
         }   
    }
    
    public IntelligentItem(Alignment alignment, int intelligence, int wisdom, int charisma, String[] languages,
         String[] senses, String communication, String[] powers, String specialPurpose, int ego, MagicItem baseItem) {
     super(baseItem.getName(), baseItem.getDescription(), baseItem.getPrice(), baseItem.getWeight(), 
             baseItem.getMaterial(), baseItem.getHp(), baseItem.getBreakDC());
     this.alignment = alignment;
     this.intelligence = intelligence;
     this.wisdom = wisdom;
     this.charisma = charisma;
     this.languages = languages;
     this.senses = senses;
     this.communication = communication;
     this.powers = powers;
     this.specialPurpose = specialPurpose;
     this.ego = ego;
     this.baseItem = baseItem;
    }

    @Override
    public Aura getAura() {
     return baseItem.getAura();
    }

    @Override
    public Activation getActivation() {
     return baseItem.getActivation();
    }

    @Override
    public Slot getSlot() {
     return baseItem.getSlot();
    }

    @Override
    public int getCL() {
     return baseItem.getCL();
    }

    @Override
    public String[] getRequirements() {
     return baseItem.getRequirements();
    }

    @Override
    public String getCost() {
     return baseItem.getCost();
    }

    public Alignment getAlignment() {
     return alignment;
    }

    public int getIntelligence() {
     return intelligence;
    }

    public int getWisdom() {
     return wisdom;
    }

    public int getCharisma() {
     return charisma;
    }

    public String[] getLanguages() {
     return languages;
    }

    public String[] getSenses() {
     return senses;
    }

    public String getCommunication() {
     return communication;
    }

    public String[] getPowers() {
     return powers;
    }

    public String getSpecialPurpose() {
     return specialPurpose;
    }

    public int getEgo() {
     return ego;
    }

    public MagicItem getBaseItem() {
     return baseItem;
    }

}

