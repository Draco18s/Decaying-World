package draco18s.decay;

import java.lang.reflect.Field;

import com.xcompwiz.mystcraft.api.MystAPI;
import com.xcompwiz.mystcraft.api.MystObjects;
import com.xcompwiz.mystcraft.api.instability.IInstabilityFactory;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;
import com.xcompwiz.mystcraft.api.symbol.IGrammarAPI;
import com.xcompwiz.mystcraft.api.symbol.words.DrawableWord;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import draco18s.decay.blocks.*;
import draco18s.decay.blocks.decays.*;
import draco18s.decay.client.*;
import draco18s.decay.entities.*;
import draco18s.decay.instability.*;
import draco18s.decay.instability.providers.*;
import draco18s.decay.instability.symbols.*;
import draco18s.decay.items.*;
import draco18s.decay.network.PacketHandler;

@Mod(modid = "DecayingWorld", name = "Decaying World", version = "4.1.0", dependencies = "required-after:Mystcraft")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
        clientPacketHandlerSpec = @SidedPacketHandler(channels = {"MoreDecay"}, packetHandler = PacketHandler.class),
        serverPacketHandlerSpec = @SidedPacketHandler(channels = {"MoreDecay"}, packetHandler = PacketHandler.class))
public class DecayingWorld
{
    @Instance("CustomDecay")
    public static DecayingWorld instance;
    public static Block metadataTextures;
    public static Block silverDecay;
    public static Block pillarDecay;
    public static Block yellowDecay;
    public static Block greenDecay;
    public static Block starDecay;
    public static Block wormDecay;
    public static Block wormInterior;
    public static Block moltenDecay;
    public static Block volcanoBuilder;
    public static Block iceIX;
    public static Block poisonWaterStill;
    public static Block poisonWaterFlowing;
    public static Block glowDecay;
    public static Block mazeDecay;
    public static Block mazeWalls;
    public static Block mazeWallsMat;
    public static Block starFissure;
    public static Block smogdecay;
    public static Block fogDecay;
    public static Block pfogDecay;
    public static Block sfogDecay;
    public static Block rfogDecay;
    public static Block methanedecay;
    public static Block methanegas;
    public static Block healCrystal;
    public static Block deathCrystal;
    public static Block growthFence;
    public static Block rawChaos;
    public static Block brittleDecay;
    public static Block unstableDecay;
    public static Block nitroDecay;
    public static Block nitroDecayGlowing;

    public static Block stoneUnst;
    public static Block stoneFrac;
    public static Block stoneBroke;
    public static Block stoneCobble;
    
    public static Item iceIXshard;
    public static Item iceIXbottle;
    public static Item healShard;
    public static Item deathShard;
    public static Item solidifier;
    public static Item lifebomb;
    public static Entity entTreant;
    public static boolean seedWorld = false;
    public static SeedDecay worldGen;
    public static boolean hardicenine = true;
    public static int userDecay;
    public static int userMeta;
    //public static boolean useGrammar;
    public static ITickHandler overlayGui;

    @SidedProxy(clientSide = "draco18s.decay.client.ClientProxy", serverSide = "draco18s.decay.CommonProxy")

    public static CommonProxy proxy;
    
    /*
    I recommand putting all of these ints, blocks, items, and just registration lines within a seperate class file and
    put make an init method within that class call all of these lines of code within that class. It would make the code look a lot
    cleaner.
    */
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	//System.out.println("Pre-Init DecayingWorld");
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
	        int metadataBlockID = config.getBlock("DebugTextures", 1550).getInt();
	        int silverDecayID = config.getBlock("SilverDecay", 1551).getInt();
	        int pillarDecayID = config.getBlock("PillarDecay", 1552).getInt();
	        int yellowDecayID = config.getBlock("YellowDecay", 1553).getInt();
	        int greenDecayID = config.getBlock("GreenDecay", 1554).getInt();
	        int starDecayID = config.getBlock("StaryDecay", 1555).getInt();
	        int wormDecayID = config.getBlock("WormyDecay", 1556).getInt();
	        int intID = config.getBlock("WormyDecayI", 1557).getInt();
	        int moltenDecayID = config.getBlock("moltenDecay", 1558).getInt();
	        int volcanoID = config.getBlock("volcanoBlock", 1559).getInt();
	        int iceIXid = config.getBlock("icenine", 1561).getInt();
	        //int poisonWaterID = config.getBlock("poisonWater", 1562).getInt();
	        //int poisonWater2ID = config.getBlock("poisonWaterFlowing", 1563).getInt();
	        int glowDecayID = config.getBlock("GlowingDecay", 1564).getInt();
	        int conwayDecayID = config.getBlock("MazeDecay", 1565).getInt();
	        int mazeWallsID = config.getBlock("MazeWalls", 1566).getInt();
	        int mazeWallsMatID = config.getBlock("MazeWallsMat", 1567).getInt();
	        int smogID = config.getBlock("SmogDecay", 1568).getInt();
	        int methID = config.getBlock("Methane", 1569).getInt();
	        int fogID = config.getBlock("Fog", 1570).getInt();
	        int pfogID = config.getBlock("PoisonFog", 1571).getInt();
	        int sfogID = config.getBlock("DenseFog", 1572).getInt();
	        int hcryID = config.getBlock("HealCrystal", 1573).getInt();
	        int dcryID = config.getBlock("DeathCrystal", 1574).getInt();
	        int rfogID = config.getBlock("rustFog", 1575).getInt();
	        int gfenceID = config.getBlock("growFence", 1576).getInt();
	        int chaosID = config.getBlock("rawChaos", 1577).getInt();
	        int brittleID = config.getBlock("brittleDecay", 1578).getInt();
	        int unstableDecayID = config.getBlock("unstableDecay", 1579).getInt();
	        int nitroDecayID = config.getBlock("nitroDecay", 1586).getInt();
	        //nitroDecayGlowing = 1587
	        int methemID = config.getBlock("MethaneEmitter", 1588).getInt();
	        
	        int stoneUnstID = config.getBlock("stoneU", 1580).getInt();
	        int stoneFracID = config.getBlock("stoneF", 1581).getInt();
	        int stoneBrokeID = config.getBlock("stoneB", 1582).getInt();
	        int stoneCobbleID = config.getBlock("stoneC", 1583).getInt();

	        int iceShardID = config.getItem("iceShard", 4099).getInt(); //item ID limit 4105 before potential conflict
	        int iceBottleID = config.getItem("iceBottle", 4100).getInt();
	        int hcrysID = config.getItem("healShard", 4101).getInt();
	        int dcrysID = config.getItem("deathShard", 4102).getInt();
	        int solidID = config.getItem("solidifier", 4103).getInt();
	        int lifebombID = config.getItem("lifebomb", 4104).getInt();
	        //int ??? = config.getItem("deathShard", 4105).getInt();
	        
	        Property conf = config.get(Configuration.CATEGORY_GENERAL, "SeedVanilla", false);
	        conf.comment = "Enabling this causes some decay types to be seeded in the Overworld (Silverfish, Volcanos, Starry Decay, Methane gas), Nether (Pillars, Volcanos), and End (Pillars).";
	        seedWorld = conf.getBoolean(false);
	        conf = config.get(Configuration.CATEGORY_GENERAL, "HarvestIce9", true);
	        conf.comment = "Disabling this prevent the harvest of Ice 9, to prevent greifing.\nIce 9 in inventories will also no longer able to be placed.";
	        hardicenine = conf.getBoolean(true);
	        conf = config.get(Configuration.CATEGORY_GENERAL, "UserDefinedDecay", -1);
	        conf.comment = "If you wish to include a decay-like block from another mod, specify the block Block ID and metadata.  -1 disables";
	        userDecay = conf.getInt(-1);

	        if (userDecay < -1)
	        {
	            userDecay = -1;
	        }

	        userMeta = config.get(Configuration.CATEGORY_GENERAL, "UserDefinedDecayMeta", 0).getInt() % 16;
	        
	        //conf = config.get(Configuration.CATEGORY_GENERAL, "AttemptGrammar", true);
	        //conf.comment = "Enabling attempts to register grammar with Mystcraft's CFG system";
	        //useGrammar = conf.getBoolean(true);
        config.save();
        metadataTextures = new MetadataIconReg(metadataBlockID, Material.rock);
        iceIX = new DecayIceNine(iceIXid, Material.ice);
        iceIXshard = new IceNineShard(iceShardID);
        iceIXbottle = new IceNineBottle(iceBottleID);
        silverDecay = new SilverDecay(silverDecayID, Material.rock);
        pillarDecay = new PillarDecay(pillarDecayID, Material.rock);
        yellowDecay = new YellowDecay(yellowDecayID, Material.rock);
        greenDecay = new GreenDecay(greenDecayID, Material.ground);
        starDecay = new StarDecay(starDecayID, Material.ground);
        wormDecay = new WormDecay(wormDecayID, Material.ground, Block.cobblestone.blockID);
        wormInterior = new DecayAir(intID, Material.air);
        moltenDecay = new MoltenDecay(moltenDecayID, Material.lava);
        volcanoBuilder = new VolcanoBuilder(volcanoID, Material.rock);
        //poisonWaterStill = new PoisonWaterStill(poisonWaterID);
        //poisonWaterFlowing = new PoisonWaterFlowing(poisonWater2ID);
        glowDecay = new GlowDecay(glowDecayID, Material.glass);
        mazeDecay = new MazeDecay(conwayDecayID, Material.ground);
        mazeWalls = new MazeWalls(mazeWallsID, Material.rock);
        mazeWallsMat = new MaterialBlock(mazeWallsMatID, Material.rock);
        smogdecay = new SmogDecay(smogID);
        methanegas = new Methane(methID);
        methanedecay = new MethaneEmitter(methemID);
        fogDecay = new BaseFogDecay(fogID);
        pfogDecay = new PoisonFogDecay(pfogID);
        sfogDecay = new SlowFogDecay(sfogID);
        healCrystal = new HealCrystal(hcryID);
        deathCrystal = new DeathCrystal(dcryID);
        healShard = new HealCrystalShard(hcrysID);
        deathShard = new DeathCrystalShard(dcrysID);
        rfogDecay = new RustFogDecay(rfogID);
        growthFence = new HealthFence(gfenceID, "crystal", Material.rock);
        solidifier = new ItemSolidifier(solidID);
        lifebomb = new ItemLifeBomb(lifebombID);
        rawChaos = new ChaosDecay(chaosID);
        brittleDecay = new BrittleDecay(brittleID, Material.rock);
        unstableDecay = new UnstableDecay(unstableDecayID, Material.rock);
        nitroDecay = new NitroDecay(nitroDecayID);
        nitroDecayGlowing = new NitroDecay(nitroDecayID+1).setLightValue(0.625F);

        stoneUnst = new StoneUnstable(stoneUnstID);
        stoneFrac = new StoneFrac(stoneFracID);
        stoneBroke = new StoneBroke(stoneBrokeID);
        stoneCobble = new StoneCobble(stoneCobbleID);

        EntityRegistry.registerModEntity(EntitySolidifier.class, "Solidifier", 1, this, 350, 5, false);
        EntityRegistry.registerModEntity(EntityLifeBomb.class, "Lifebomb", 2, this, 350, 5, false);
        EntityRegistry.registerModEntity(EntityTreant.class, "EntTreant", 3, this, 350, 5, false);
        EntityRegistry.registerModEntity(EntityBlinkDog.class, "EntBlinkDog", 4, this, 350, 5, false);
        EntityRegistry.registerModEntity(EntityEmpyreal.class, "EntEmpyreal", 5, this, 350, 5, false);
        EntityRegistry.registerModEntity(EntityFooDog.class, "EntFooDog", 6, this, 350, 5, false);

        /*EntityList.entityEggs.put(3, new EntityEggInfo(3, 4930341, 1584909));
        EntityList.entityEggs.put(4, new EntityEggInfo(4, 14342901, 8026845));
        EntityList.entityEggs.put(5, new EntityEggInfo(5, 16667218, 16775118));
        EntityList.entityEggs.put(6, new EntityEggInfo(6, 16438947, 16032045));*/
        EntityList.addMapping(EntityTreant.class, "Treant", 3, 4930341, 1584909);
        EntityList.addMapping(EntityBlinkDog.class, "Blink Dog", 4, 14342901, 8026845);
        EntityList.addMapping(EntityEmpyreal.class, "Empyreal", 5, 16667218, 16775118);
        EntityList.addMapping(EntityFooDog.class, "Foo Dog", 6, 16438947, 16032045);
        //LanguageRegistry.addStringLocalization(key, "en_US", "Treant");
    	proxy.loadSounds();
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        GameRegistry.registerBlock(metadataTextures, "metadataTextures");
        GameRegistry.registerBlock(silverDecay, "silverDecay");
        GameRegistry.registerBlock(pillarDecay, "pillarDecay");
        GameRegistry.registerBlock(yellowDecay, "yellowDecay");
        GameRegistry.registerBlock(greenDecay, "greenDecay");
        GameRegistry.registerBlock(starDecay, "starDecay");
        GameRegistry.registerBlock(wormDecay, "wormDecay");
        GameRegistry.registerBlock(wormInterior, "wormDecayI");
        GameRegistry.registerBlock(moltenDecay, "moltenDecay");
        GameRegistry.registerBlock(volcanoBuilder, "volcanoBuilder");
        GameRegistry.registerBlock(iceIX, "icenine");
        //GameRegistry.registerBlock(poisonWaterStill, "poisonWaterStill");
        //GameRegistry.registerBlock(poisonWaterFlowing, "poisonWaterFlow");
        GameRegistry.registerBlock(glowDecay, "glowDecay");
        GameRegistry.registerBlock(mazeDecay, "conwayDecay");
        GameRegistry.registerBlock(mazeWalls, "mazeWalls");
        GameRegistry.registerBlock(mazeWallsMat, "mazeWallsMat");
        GameRegistry.registerBlock(smogdecay, "Smog");
        GameRegistry.registerBlock(methanegas, "Methane");
        GameRegistry.registerBlock(methanedecay, "Methane Emitter");
        GameRegistry.registerBlock(fogDecay, "Fog");
        GameRegistry.registerBlock(pfogDecay, "Poison Fog");
        GameRegistry.registerBlock(sfogDecay, "Dense Fog");
        GameRegistry.registerBlock(healCrystal, "Healing Crystal");
        GameRegistry.registerBlock(deathCrystal, "Death Crystal");
        GameRegistry.registerBlock(rfogDecay, "Corrosive Fog");
        GameRegistry.registerBlock(growthFence, "Growth Fence");
        GameRegistry.registerBlock(rawChaos, "Raw Chaos");
        GameRegistry.registerBlock(brittleDecay, "Brittle Decay");
        GameRegistry.registerBlock(unstableDecay, "Unstable Decay");
        GameRegistry.registerBlock(stoneUnst, "Stone (Unstable)");
        GameRegistry.registerBlock(stoneFrac, "Stone (Fractured)");
        GameRegistry.registerBlock(stoneBroke, "Stone (Broken)");
        GameRegistry.registerBlock(stoneCobble, "Stone (Cobble)");
        GameRegistry.registerBlock(nitroDecay, "Nitro Decay");
        
        GameRegistry.registerItem(iceIXshard, "iceIXshard");
        GameRegistry.registerItem(healShard, "healshard");
        GameRegistry.registerItem(deathShard, "deathshard");
        GameRegistry.registerItem(iceIXbottle, "iceIXbottle");
        GameRegistry.registerItem(solidifier, "Energy Absorber");
        GameRegistry.registerItem(lifebomb, "Life Bomb");

        LanguageRegistry.addName(metadataTextures, "Metadata Debug Texture Holder");
        LanguageRegistry.addName(silverDecay, "Silver Decay");
        LanguageRegistry.addName(pillarDecay, "Bedrock Pillars");
        LanguageRegistry.addName(yellowDecay, "Yellow Decay");
        LanguageRegistry.addName(greenDecay, "Green Decay");
        LanguageRegistry.addName(starDecay, "Starry Decay");
        LanguageRegistry.addName(wormDecay, "Wormry Decay");
        LanguageRegistry.addName(wormInterior, "Worm Flesh");
        LanguageRegistry.addName(moltenDecay, "Molten Decay");
        LanguageRegistry.addName(volcanoBuilder, "Volcano Block");
        LanguageRegistry.addName(iceIX, "Ice-Nine");
        LanguageRegistry.addName(iceIXshard, "Shard of Ice-Nine");
        LanguageRegistry.addName(iceIXbottle, "Bottle of Ice-Nine");
        LanguageRegistry.addName(healShard, "Healing Shard");
        LanguageRegistry.addName(deathShard, "Death Shard");
        //LanguageRegistry.addName(poisonWaterStill, "Poisoned Water");
        //LanguageRegistry.addName(poisonWaterFlowing, "Poisoned Water");
        LanguageRegistry.addName(glowDecay, "Glowing Decay");
        LanguageRegistry.addName(mazeDecay, "Conway Decay");
        LanguageRegistry.addName(mazeWalls, "Maze Walls");
        LanguageRegistry.addName(mazeWallsMat, "Maze Walls Mat");
        LanguageRegistry.addName(smogdecay, "Smog");
        LanguageRegistry.addName(methanegas, "Methane");
        LanguageRegistry.addName(methanedecay, "Methane Emitter");
        LanguageRegistry.addName(fogDecay, "Fog");
        LanguageRegistry.addName(pfogDecay, "Poison Fog");
        LanguageRegistry.addName(sfogDecay, "Dense Fog");
        LanguageRegistry.addName(healCrystal, "Healing Crystal");
        LanguageRegistry.addName(deathCrystal, "Death Crystal");
        LanguageRegistry.addName(rfogDecay, "Corrosive Fog");
        LanguageRegistry.addName(growthFence, "Growth Fence");
        LanguageRegistry.addName(solidifier, "Energy Absorber");
        LanguageRegistry.addName(lifebomb, "Life Bomb");
        LanguageRegistry.addName(rawChaos, "Raw Chaos");
        LanguageRegistry.addName(brittleDecay, "Brittle Decay"); 
        LanguageRegistry.addName(unstableDecay, "Unstable Decay");
        LanguageRegistry.addName(stoneUnst, "Stone (Unstable)");
        LanguageRegistry.addName(stoneFrac, "Stone (Fractured)");
        LanguageRegistry.addName(stoneBroke, "Stone (Broken)");
        LanguageRegistry.addName(stoneCobble, "Stone (Cobble)");
        LanguageRegistry.addName(nitroDecay, "Nitro Decay");

        GameRegistry.registerTileEntity(MaterialEntity.class, "mazeMaterial");
        GameRegistry.registerTileEntity(DeathCryEnt.class, "deathCrystal");
        
        /*EntityRegistry.addSpawn(EntityBlinkDog.class, 8, 2, 4, EnumCreatureType.monster, BiomeGenBase.plains);
        EntityRegistry.addSpawn(EntityBlinkDog.class, 2, 2, 4, EnumCreatureType.monster, BiomeGenBase.mushroomIsland);
        EntityRegistry.addSpawn(EntityBlinkDog.class, 2, 2, 4, EnumCreatureType.monster, BiomeGenBase.mushroomIslandShore);
        EntityRegistry.addSpawn(EntityBlinkDog.class, 6, 2, 4, EnumCreatureType.monster, BiomeGenBase.beach);
        EntityRegistry.addSpawn(EntityBlinkDog.class, 6, 2, 4, EnumCreatureType.monster, BiomeGenBase.desert);
        EntityRegistry.addSpawn(EntityBlinkDog.class, 6, 2, 4, EnumCreatureType.monster, BiomeGenBase.forest);
        EntityRegistry.addSpawn(EntityBlinkDog.class, 6, 2, 4, EnumCreatureType.monster, BiomeGenBase.icePlains);
        
        EntityRegistry.addSpawn(EntityTreant.class, 3, 1, 1, EnumCreatureType.monster, BiomeGenBase.forest);
        EntityRegistry.addSpawn(EntityTreant.class, 2, 1, 1, EnumCreatureType.monster, BiomeGenBase.forestHills);
        EntityRegistry.addSpawn(EntityTreant.class, 3, 1, 1, EnumCreatureType.monster, BiomeGenBase.jungle);
        EntityRegistry.addSpawn(EntityTreant.class, 2, 1, 1, EnumCreatureType.monster, BiomeGenBase.jungleHills);
        EntityRegistry.addSpawn(EntityTreant.class, 1, 1, 1, EnumCreatureType.monster, BiomeGenBase.mushroomIsland);
        EntityRegistry.addSpawn(EntityTreant.class, 1, 1, 1, EnumCreatureType.monster, BiomeGenBase.mushroomIslandShore);
        EntityRegistry.addSpawn(EntityTreant.class, 2, 1, 1, EnumCreatureType.monster, BiomeGenBase.swampland);
        EntityRegistry.addSpawn(EntityTreant.class, 3, 1, 1, EnumCreatureType.monster, BiomeGenBase.taiga);
        EntityRegistry.addSpawn(EntityTreant.class, 2, 1, 1, EnumCreatureType.monster, BiomeGenBase.taigaHills);
        
        EntityRegistry.addSpawn(EntityEmpyreal.class, 3, 1, 1, EnumCreatureType.monster, BiomeGenBase.plains);
        EntityRegistry.addSpawn(EntityEmpyreal.class, 5, 1, 1, EnumCreatureType.monster, BiomeGenBase.desert);
        EntityRegistry.addSpawn(EntityEmpyreal.class, 4, 1, 1, EnumCreatureType.monster, BiomeGenBase.desertHills);
        EntityRegistry.addSpawn(EntityEmpyreal.class, 2, 1, 1, EnumCreatureType.monster, BiomeGenBase.extremeHills);
        EntityRegistry.addSpawn(EntityEmpyreal.class, 2, 1, 1, EnumCreatureType.monster, BiomeGenBase.extremeHillsEdge);
        EntityRegistry.addSpawn(EntityEmpyreal.class, 3, 1, 2, EnumCreatureType.monster, BiomeGenBase.hell);
        EntityRegistry.addSpawn(EntityEmpyreal.class, 1, 1, 1, EnumCreatureType.monster, BiomeGenBase.beach);*/
        
        //duplicate and replace "monster" with "creature" for daytime spawning

        Block.setBurnProperties(methanedecay.blockID, 500, 50);

        if (seedWorld)
        {
            worldGen = new SeedDecay();
            GameRegistry.registerWorldGenerator(worldGen);
        }

        shardRecipes();
        ItemStack shard = new ItemStack(healShard);
        GameRegistry.addShapelessRecipe(new ItemStack(healCrystal), shard, shard, shard, shard, shard, shard, shard, shard, shard);
        GameRegistry.addShapedRecipe(new ItemStack(growthFence, 2), "SFS", "SFS", 'S', shard, 'F', new ItemStack(Block.fence));
        shard = new ItemStack(deathShard);
        GameRegistry.addShapelessRecipe(new ItemStack(deathCrystal), shard, shard, shard, shard, shard, shard, shard, shard, shard);
        GameRegistry.addShapelessRecipe(new ItemStack(solidifier), shard, new ItemStack(Item.snowball));
        GameRegistry.addShapedRecipe(new ItemStack(lifebomb), " S ", "dBd", " S ", 'd',shard, 'B', new ItemStack(Item.snowball), 'S', new ItemStack(healShard));

        System.out.println("Attempting to register with Mystcraft");
        if (MystAPI.instability != null)
        {
            System.out.println("Myst API found");
            starFissure = MystObjects.star_fissure;
            MystAPI.instability.registerInstability(new EffectPillarsProvider());
            MystAPI.instability.registerInstability(new EffectVolcanoProvider());
            MystAPI.instability.registerInstability(new EffectIceNineProvider());
            MystAPI.instability.registerInstability(new EffectSilverDecayProvider());
            MystAPI.instability.registerInstability(new EffectYellowDecayProvider());
            MystAPI.instability.registerInstability(new EffectStarDecayProvider());
            MystAPI.instability.registerInstability(new EffectWormDecayProvider());
            MystAPI.instability.registerInstability(new EffectGreenDecayProvider());
            MystAPI.instability.registerInstability(new EffectNearSunProvider());
            MystAPI.instability.registerInstability(new EffectFrozenWorldProvider());
            MystAPI.instability.registerInstability(new EffectStarDecayProvider());
            MystAPI.instability.registerInstability(new EffectSmogProvider());
            MystAPI.instability.registerInstability(new EffectMethaneProvider());
            MystAPI.instability.registerInstability(new EffectPoisonFogProvider());
            MystAPI.instability.registerInstability(new EffectCorrosionFogProvider());
            MystAPI.instability.registerInstability(new EffectDenseFogProvider());
            MystAPI.instability.registerInstability(new EffectPositiveEnergyProvider());
            MystAPI.instability.registerInstability(new EffectNegativeEnergyProvider());
            MystAPI.instability.registerInstability(new EffectUnstableDecayProvider());
            MystAPI.instability.registerInstability(new EffectBrittleDecayProvider());
            MystAPI.instability.registerInstability(new EffectNitroProvider());
            MystAPI.symbol.registerWord("Puzzle", new DrawableWord(new Integer[] {4, 5, 6, 7, 11, 12, 14, 17, 20, 22}));
            MystAPI.symbol.registerWord("Death", new DrawableWord(new Integer[] {4, 5, 9, 12, 14, 15}));
            MystAPI.symbol.registerWord("Life", new DrawableWord(new Integer[] {6, 7, 19, 20, 21, 22}));
            MystAPI.symbol.registerWord("Contain", new DrawableWord(new Integer[] {4, 5, 6, 7, 9, 13, 19, 23, 40, 41, 42, 43, 44}));
            
            /*ItemStack items = new ItemStack(Item.emerald, 15);
            IAgeSymbol symbol = new SymbolMaze();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.4F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);
			symbol = new SymbolWorm();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.4F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);
			symbol = new SymbolFrozen();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.4F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);
			symbol = new SymbolExtremeTemperatures();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.4F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);
			symbol = new SymbolGlow();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.4F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);
			symbol = new SymbolSilver();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.4F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);
			symbol = new SymbolSmog();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.4F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);
			symbol = new SymbolDenseFog();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.4F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);
			symbol = new SymbolMethane();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.4F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);
			items = new ItemStack(Item.emerald, 30);
			symbol = new SymbolMolten();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.2F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);
			symbol = new SymbolPositiveEnergy();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.1F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);
			symbol = new SymbolNegativeEnergy();
            MystAPI.symbol.registerSymbol(symbol, true);
            MystAPI.symbolValues.setSymbolIsTradable(symbol, true);
			MystAPI.symbolValues.setSymbolItemRarity(symbol, 0.1F);
			MystAPI.symbolValues.setSymbolTradeItem(symbol, items);*/
            MystAPI.symbol.registerSymbol(new SymbolMaze(), true);
            MystAPI.symbol.registerSymbol(new SymbolWorm(), true);
            MystAPI.symbol.registerSymbol(new SymbolFrozen(), true);
	        MystAPI.symbol.registerSymbol(new SymbolExtremeTemperatures(), true);
            MystAPI.symbol.registerSymbol(new SymbolMolten(), true);
            MystAPI.symbol.registerSymbol(new SymbolGlow(), true);
            MystAPI.symbol.registerSymbol(new SymbolSilver(), true);
            MystAPI.symbol.registerSymbol(new SymbolSmog(), true);
            MystAPI.symbol.registerSymbol(new SymbolDenseFog(), true);
            MystAPI.symbol.registerSymbol(new SymbolMethane(), true);
            MystAPI.symbol.registerSymbol(new SymbolPositiveEnergy(), true);
            MystAPI.symbol.registerSymbol(new SymbolNegativeEnergy(), true);
			if(userDecay != -1) {
	            MystAPI.instability.registerInstability(new EffectModDecayProvider());
				//MystAPI.symbol.registerSymbol(new SymbolModifiable());
			}
			
			/*Field[] f = MystAPI.class.getDeclaredFields();
			boolean hasGrammar = false;
			for(int fi=0; fi < f.length; fi++) {
				if(f[fi].getName() == "grammar") {
					hasGrammar = true;
				}
			}*/
			//System.out.println("MystAPI has grammar: " + hasGrammar);
			if(MystAPI.grammar != null) {
				MystAPI.grammar.registerGrammarRule("Populator", 0.5F, new String[] {"Populator","DecaySelection"});

				MystAPI.grammar.registerGrammarRule("DecaySelection", 2.0F, new String[] {"DecaySilver"});
				MystAPI.grammar.registerGrammarRule("DecaySelection", 1.0F, new String[] {"MethanePockets"});
				MystAPI.grammar.registerGrammarRule("DecaySelection", 1.0F, new String[0]);
				MystAPI.grammar.registerGrammarRule("DecaySelection", 0.5F, new String[] {"DecayMolten"});
				MystAPI.grammar.registerGrammarRule("DecaySelection", 0.2F, new String[] {"MazeMaterial", "MazeMaterial", "MazeMaterial", "MazeMaterial", "DecayMaze"});
				MystAPI.grammar.registerGrammarRule("DecaySelection", 0.1F, new String[] {"MazeMaterial", "DecayWorm"});
				
				MystAPI.grammar.registerGrammarRule("MazeMaterial", 1.0F, new String[] {IGrammarAPI.BLOCK_STRUCTURE});
				MystAPI.grammar.registerGrammarRule("MazeMaterial", 3.0F, new String[0]);
			}
			
        }
        else
        {
            starFissure = Block.endPortal;
        }
        //System.out.println("Finished loading DecayingWorld");
        MinecraftForge.EVENT_BUS.register(new EventHandlers());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	//System.out.println("PostInit DecayingWorld");
    	proxy.registerRenders();
    	((ChaosDecay)(rawChaos)).setupIDs();
    }

    private void shardRecipes()
    {
    	System.out.println("Shard Recipe Construction");
        ItemStack oneShard = new ItemStack(iceIXshard);
        ItemStack bottle = new ItemStack(Item.glassBottle);
        ItemStack IXbottle1 = new ItemStack(iceIXbottle, 1, 16);
        ItemStack IXbottle2 = new ItemStack(iceIXbottle, 1, 15);
        ItemStack IXbottle3 = new ItemStack(iceIXbottle, 1, 14);
        ItemStack IXbottle4 = new ItemStack(iceIXbottle, 1, 13);
        ItemStack IXbottle5 = new ItemStack(iceIXbottle, 1, 12);
        ItemStack IXbottle6 = new ItemStack(iceIXbottle, 1, 11);
        ItemStack IXbottle7 = new ItemStack(iceIXbottle, 1, 10);
        ItemStack IXbottle8 = new ItemStack(iceIXbottle, 1, 9);
        ItemStack IXbottle9 = new ItemStack(iceIXbottle, 1, 8);
        ItemStack IXbottle10 = new ItemStack(iceIXbottle, 1, 7);
        ItemStack IXbottle11 = new ItemStack(iceIXbottle, 1, 6);
        ItemStack IXbottle12 = new ItemStack(iceIXbottle, 1, 5);
        ItemStack IXbottle13 = new ItemStack(iceIXbottle, 1, 4);
        ItemStack IXbottle14 = new ItemStack(iceIXbottle, 1, 3);
        ItemStack IXbottle15 = new ItemStack(iceIXbottle, 1, 2);
        ItemStack IXbottle16 = new ItemStack(iceIXbottle, 1, 1);
        GameRegistry.addShapelessRecipe(IXbottle1, oneShard, bottle);
        GameRegistry.addShapelessRecipe(IXbottle2, oneShard, IXbottle1);
        GameRegistry.addShapelessRecipe(IXbottle3, oneShard, IXbottle2);
        GameRegistry.addShapelessRecipe(IXbottle4, oneShard, IXbottle3);
        GameRegistry.addShapelessRecipe(IXbottle5, oneShard, IXbottle4);
        GameRegistry.addShapelessRecipe(IXbottle6, oneShard, IXbottle5);
        GameRegistry.addShapelessRecipe(IXbottle7, oneShard, IXbottle6);
        GameRegistry.addShapelessRecipe(IXbottle8, oneShard, IXbottle7);
        GameRegistry.addShapelessRecipe(IXbottle9, oneShard, IXbottle8);
        GameRegistry.addShapelessRecipe(IXbottle10, oneShard, IXbottle9);
        GameRegistry.addShapelessRecipe(IXbottle11, oneShard, IXbottle10);
        GameRegistry.addShapelessRecipe(IXbottle12, oneShard, IXbottle11);
        GameRegistry.addShapelessRecipe(IXbottle13, oneShard, IXbottle12);
        GameRegistry.addShapelessRecipe(IXbottle14, oneShard, IXbottle13);
        GameRegistry.addShapelessRecipe(IXbottle15, oneShard, IXbottle14);
        GameRegistry.addShapelessRecipe(IXbottle16, oneShard, IXbottle15);
        GameRegistry.addShapelessRecipe(IXbottle2, oneShard, oneShard, bottle);
        GameRegistry.addShapelessRecipe(IXbottle3, oneShard, oneShard, oneShard, bottle);
        GameRegistry.addShapelessRecipe(IXbottle4, oneShard, oneShard, oneShard, oneShard, bottle);
        GameRegistry.addShapelessRecipe(IXbottle5, oneShard, oneShard, oneShard, oneShard, oneShard, bottle);
        GameRegistry.addShapelessRecipe(IXbottle6, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, bottle);
        GameRegistry.addShapelessRecipe(IXbottle7, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, bottle);
        GameRegistry.addShapelessRecipe(IXbottle8, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, bottle);
        GameRegistry.addShapelessRecipe(IXbottle3, oneShard, oneShard, IXbottle1);
        GameRegistry.addShapelessRecipe(IXbottle4, oneShard, oneShard, oneShard, IXbottle1);
        GameRegistry.addShapelessRecipe(IXbottle5, oneShard, oneShard, oneShard, oneShard, IXbottle1);
        GameRegistry.addShapelessRecipe(IXbottle6, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle1);
        GameRegistry.addShapelessRecipe(IXbottle7, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle1);
        GameRegistry.addShapelessRecipe(IXbottle8, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle1);
        GameRegistry.addShapelessRecipe(IXbottle9, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle1);
        GameRegistry.addShapelessRecipe(IXbottle4, oneShard, oneShard, IXbottle2);
        GameRegistry.addShapelessRecipe(IXbottle5, oneShard, oneShard, oneShard, IXbottle2);
        GameRegistry.addShapelessRecipe(IXbottle6, oneShard, oneShard, oneShard, oneShard, IXbottle2);
        GameRegistry.addShapelessRecipe(IXbottle7, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle2);
        GameRegistry.addShapelessRecipe(IXbottle8, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle2);
        GameRegistry.addShapelessRecipe(IXbottle9, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle2);
        GameRegistry.addShapelessRecipe(IXbottle10, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle2);
        GameRegistry.addShapelessRecipe(IXbottle5, oneShard, oneShard, IXbottle3);
        GameRegistry.addShapelessRecipe(IXbottle6, oneShard, oneShard, oneShard, IXbottle3);
        GameRegistry.addShapelessRecipe(IXbottle7, oneShard, oneShard, oneShard, oneShard, IXbottle3);
        GameRegistry.addShapelessRecipe(IXbottle8, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle3);
        GameRegistry.addShapelessRecipe(IXbottle9, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle3);
        GameRegistry.addShapelessRecipe(IXbottle10, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle3);
        GameRegistry.addShapelessRecipe(IXbottle11, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle3);
        GameRegistry.addShapelessRecipe(IXbottle6, oneShard, oneShard, IXbottle4);
        GameRegistry.addShapelessRecipe(IXbottle7, oneShard, oneShard, oneShard, IXbottle4);
        GameRegistry.addShapelessRecipe(IXbottle8, oneShard, oneShard, oneShard, oneShard, IXbottle4);
        GameRegistry.addShapelessRecipe(IXbottle9, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle4);
        GameRegistry.addShapelessRecipe(IXbottle10, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle4);
        GameRegistry.addShapelessRecipe(IXbottle11, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle4);
        GameRegistry.addShapelessRecipe(IXbottle12, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle4);
        GameRegistry.addShapelessRecipe(IXbottle7, oneShard, oneShard, IXbottle5);
        GameRegistry.addShapelessRecipe(IXbottle8, oneShard, oneShard, oneShard, IXbottle5);
        GameRegistry.addShapelessRecipe(IXbottle9, oneShard, oneShard, oneShard, oneShard, IXbottle5);
        GameRegistry.addShapelessRecipe(IXbottle10, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle5);
        GameRegistry.addShapelessRecipe(IXbottle11, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle5);
        GameRegistry.addShapelessRecipe(IXbottle12, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle5);
        GameRegistry.addShapelessRecipe(IXbottle13, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle5);
        GameRegistry.addShapelessRecipe(IXbottle8, oneShard, oneShard, IXbottle6);
        GameRegistry.addShapelessRecipe(IXbottle9, oneShard, oneShard, oneShard, IXbottle6);
        GameRegistry.addShapelessRecipe(IXbottle10, oneShard, oneShard, oneShard, oneShard, IXbottle6);
        GameRegistry.addShapelessRecipe(IXbottle11, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle6);
        GameRegistry.addShapelessRecipe(IXbottle12, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle6);
        GameRegistry.addShapelessRecipe(IXbottle13, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle6);
        GameRegistry.addShapelessRecipe(IXbottle14, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle6);
        GameRegistry.addShapelessRecipe(IXbottle9, oneShard, oneShard, IXbottle7);
        GameRegistry.addShapelessRecipe(IXbottle10, oneShard, oneShard, oneShard, IXbottle7);
        GameRegistry.addShapelessRecipe(IXbottle11, oneShard, oneShard, oneShard, oneShard, IXbottle7);
        GameRegistry.addShapelessRecipe(IXbottle12, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle7);
        GameRegistry.addShapelessRecipe(IXbottle13, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle7);
        GameRegistry.addShapelessRecipe(IXbottle14, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle7);
        GameRegistry.addShapelessRecipe(IXbottle15, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle7);
        GameRegistry.addShapelessRecipe(IXbottle10, oneShard, oneShard, IXbottle8);
        GameRegistry.addShapelessRecipe(IXbottle11, oneShard, oneShard, oneShard, IXbottle8);
        GameRegistry.addShapelessRecipe(IXbottle12, oneShard, oneShard, oneShard, oneShard, IXbottle8);
        GameRegistry.addShapelessRecipe(IXbottle13, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle8);
        GameRegistry.addShapelessRecipe(IXbottle14, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle8);
        GameRegistry.addShapelessRecipe(IXbottle15, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle8);
        GameRegistry.addShapelessRecipe(IXbottle16, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle8);
        GameRegistry.addShapelessRecipe(IXbottle11, oneShard, oneShard, IXbottle9);
        GameRegistry.addShapelessRecipe(IXbottle12, oneShard, oneShard, oneShard, IXbottle9);
        GameRegistry.addShapelessRecipe(IXbottle13, oneShard, oneShard, oneShard, oneShard, IXbottle9);
        GameRegistry.addShapelessRecipe(IXbottle14, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle9);
        GameRegistry.addShapelessRecipe(IXbottle15, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle9);
        GameRegistry.addShapelessRecipe(IXbottle16, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle9);
        GameRegistry.addShapelessRecipe(IXbottle12, oneShard, oneShard, IXbottle10);
        GameRegistry.addShapelessRecipe(IXbottle13, oneShard, oneShard, oneShard, IXbottle10);
        GameRegistry.addShapelessRecipe(IXbottle14, oneShard, oneShard, oneShard, oneShard, IXbottle10);
        GameRegistry.addShapelessRecipe(IXbottle15, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle10);
        GameRegistry.addShapelessRecipe(IXbottle16, oneShard, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle10);
        GameRegistry.addShapelessRecipe(IXbottle13, oneShard, oneShard, IXbottle11);
        GameRegistry.addShapelessRecipe(IXbottle14, oneShard, oneShard, oneShard, IXbottle11);
        GameRegistry.addShapelessRecipe(IXbottle15, oneShard, oneShard, oneShard, oneShard, IXbottle11);
        GameRegistry.addShapelessRecipe(IXbottle16, oneShard, oneShard, oneShard, oneShard, oneShard, IXbottle11);
        GameRegistry.addShapelessRecipe(IXbottle14, oneShard, oneShard, IXbottle12);
        GameRegistry.addShapelessRecipe(IXbottle15, oneShard, oneShard, oneShard, IXbottle12);
        GameRegistry.addShapelessRecipe(IXbottle16, oneShard, oneShard, oneShard, oneShard, IXbottle12);
        GameRegistry.addShapelessRecipe(IXbottle15, oneShard, oneShard, IXbottle13);
        GameRegistry.addShapelessRecipe(IXbottle16, oneShard, oneShard, oneShard, IXbottle13);
        GameRegistry.addShapelessRecipe(IXbottle16, oneShard, oneShard, IXbottle14);
    }

    public static boolean evilmobs(EntityLivingBase var5) {
    	return (var5 instanceof EntitySkeleton || var5 instanceof EntityZombie || var5 instanceof EntityGhast || var5 instanceof EntityPigZombie);
    }
    public static boolean goodmobs(EntityLivingBase var5) {
    	return (var5 instanceof EntityTreant || var5 instanceof EntityBlinkDog || var5 instanceof EntityEmpyreal || var5 instanceof EntityFooDog);
    }

    public static void drawLine3D(World world, int block, int x1, int y1, int z1, int x2, int y2, int z2) {
    	drawLine3D(world, block, x1, y1, z1, x2, y2, z2, false, false);
    }

    public static void drawLine3D(World world, int block, int x1, int y1, int z1, int x2, int y2, int z2, boolean incremental) {
    	drawLine3D(world, block, x1, y1, z1, x2, y2, z2, incremental, false);
    }
    
    public static void drawLine3D(World world, int block, int x1, int y1, int z1, int x2, int y2, int z2, boolean incremental, boolean resist) {
    	int i, dx, dy, dz, m, n, x_inc, y_inc, z_inc,err_1, err_2, dx2, dy2, dz2, l;
    	float ll;
    	int pixel[] = new int[3];
    	pixel[0] = x1;
    	pixel[1] = y1;
    	pixel[2] = z1;
		dx = x2 - x1;
		dy = y2 - y1;
		dz = z2 - z1;
		x_inc = (dx < 0) ? -1 : 1;
		l = Math.abs(dx);
		y_inc = (dy < 0) ? -1 : 1;
		m = Math.abs(dy);
		z_inc = (dz < 0) ? -1 : 1;
		n = Math.abs(dz);
		dx2 = l << 1;
		dy2 = m << 1;
		dz2 = n << 1;
		float res = 0; 
		if ((l >= m) && (l >= n)) {
		    err_1 = dy2 - l;
		    err_2 = dz2 - l;
			ll = l;
		    for (i = 0; i < ll; i++) {
		    	res = PUT_PIXEL(world, block, pixel);
		        if (err_1 > 0) {
		            pixel[1] += y_inc;
		            err_1 -= dx2;
		        }
		        if (err_2 > 0) {
		            pixel[2] += z_inc;
		            err_2 -= dx2;
		        }
		        err_1 += dy2;
		        err_2 += dz2;
		        pixel[0] += x_inc;
		        ll -= res;
		    }
		} else if ((m >= l) && (m >= n)) {
		    err_1 = dx2 - m;
		    err_2 = dz2 - m;
		    ll = m;
		    for (i = 0; i < ll; i++) {
		    	res = PUT_PIXEL(world, block, pixel);
		        if (err_1 > 0) {
		            pixel[0] += x_inc;
		            err_1 -= dy2;
		        }
		        if (err_2 > 0) {
		            pixel[2] += z_inc;
		            err_2 -= dy2;
		        }
		        err_1 += dx2;
		        err_2 += dz2;
		        pixel[1] += y_inc;
		        ll -= res;
		    }
		} else {
		    err_1 = dy2 - n;
		    err_2 = dx2 - n;
		    ll = n;
		    for (i = 0; i < ll; i++) {
		    	res = PUT_PIXEL(world, block, pixel);
		        if (err_1 > 0) {
		            pixel[1] += y_inc;
		            err_1 -= dz2;
		        }
		        if (err_2 > 0) {
		            pixel[0] += x_inc;
		            err_2 -= dz2;
		        }
		        err_1 += dy2;
		        err_2 += dx2;
		        pixel[2] += z_inc;
		        ll -= res;
		    }
		}
		PUT_PIXEL(world, block, pixel);
    }
    
    private static float PUT_PIXEL(World w, int b, int[] p) {
    	return PUT_PIXEL(w, b, p, false);
    }
    
    private static float PUT_PIXEL(World w, int b, int[] p, boolean inc) {
    	int s = w.getBlockId(p[0], p[1], p[2]);
    	float r = 1000;
    	if(s == Block.stone.blockID || s == stoneUnst.blockID || s == Block.glass.blockID) {
    		r = Block.blocksList[s].blockResistance;
    		w.setBlock(p[0], p[1], p[2], b);
    	}
    	if(inc) {
    		if(s == b) {
        		r = Block.blocksList[s].blockResistance;
    			w.setBlock(p[0], p[1], p[2], b+1);
    		}
    		else if(s == b+1) {
        		r = Block.blocksList[s].blockResistance;
    			w.setBlock(p[0], p[1], p[2], b+2);
    		}
    	}
    	return r / 10.0F;
    }
    
    public static void damageNeighbors(World world, int x, int y, int z) {
    	int s = world.getBlockId(x+1, y, z);
    	if(s == Block.stone.blockID) {
    		s = DecayingWorld.stoneUnst.blockID;
    	}
    	else if(s == DecayingWorld.stoneUnst.blockID) {
    		s = DecayingWorld.stoneFrac.blockID;
    	}
    	else if(s == DecayingWorld.stoneFrac.blockID) {
    		s = DecayingWorld.stoneBroke.blockID;
    	}
    	else if(s == DecayingWorld.stoneBroke.blockID || s == Block.cobblestone.blockID) {
    		s = DecayingWorld.stoneCobble.blockID;
    	}
    	world.setBlock(x+1, y, z, s);
    	
    	s = world.getBlockId(x-1, y, z);
    	if(s == Block.stone.blockID) {
    		s = DecayingWorld.stoneUnst.blockID;
    	}
    	else if(s == DecayingWorld.stoneUnst.blockID) {
    		s = DecayingWorld.stoneFrac.blockID;
    	}
    	else if(s == DecayingWorld.stoneFrac.blockID) {
    		s = DecayingWorld.stoneBroke.blockID;
    	}
    	else if(s == DecayingWorld.stoneBroke.blockID || s == Block.cobblestone.blockID) {
    		s = DecayingWorld.stoneCobble.blockID;
    	}
    	world.setBlock(x-1, y, z, s);
    	
    	s = world.getBlockId(x, y, z+1);
    	if(s == Block.stone.blockID) {
    		s = DecayingWorld.stoneUnst.blockID;
    	}
    	else if(s == DecayingWorld.stoneUnst.blockID) {
    		s = DecayingWorld.stoneFrac.blockID;
    	}
    	else if(s == DecayingWorld.stoneFrac.blockID) {
    		s = DecayingWorld.stoneBroke.blockID;
    	}
    	else if(s == DecayingWorld.stoneBroke.blockID || s == Block.cobblestone.blockID) {
    		s = DecayingWorld.stoneCobble.blockID;
    	}
    	world.setBlock(x, y, z+1, s);
    	
    	s = world.getBlockId(x, y, z-1);
    	if(s == Block.stone.blockID) {
    		s = DecayingWorld.stoneUnst.blockID;
    	}
    	else if(s == DecayingWorld.stoneUnst.blockID) {
    		s = DecayingWorld.stoneFrac.blockID;
    	}
    	else if(s == DecayingWorld.stoneFrac.blockID) {
    		s = DecayingWorld.stoneBroke.blockID;
    	}
    	else if(s == DecayingWorld.stoneBroke.blockID || s == Block.cobblestone.blockID) {
    		s = DecayingWorld.stoneCobble.blockID;
    	}
    	world.setBlock(x, y, z-1, s);
    	
    	s = world.getBlockId(x, y+1, z);
    	if(s == Block.stone.blockID) {
    		s = DecayingWorld.stoneUnst.blockID;
    	}
    	else if(s == DecayingWorld.stoneUnst.blockID) {
    		s = DecayingWorld.stoneFrac.blockID;
    	}
    	else if(s == DecayingWorld.stoneFrac.blockID) {
    		s = DecayingWorld.stoneBroke.blockID;
    	}
    	else if(s == DecayingWorld.stoneBroke.blockID || s == Block.cobblestone.blockID) {
    		s = DecayingWorld.stoneCobble.blockID;
    	}
    	world.setBlock(x, y+1, z, s);
    	//if(world.isRemote) {
    		world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "decayingworld:rock", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
    	//}
    }
    
}
