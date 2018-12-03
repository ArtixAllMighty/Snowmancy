package bl4ckscor3.mod.snowmancy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bl4ckscor3.mod.snowmancy.block.BlockSnowmanBuilder;
import bl4ckscor3.mod.snowmancy.tileentity.TileEntitySnowmanBuilder;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@Mod(modid=Snowmancy.MODID, name=Snowmancy.NAME, version=Snowmancy.VERSION, acceptedMinecraftVersions=Snowmancy.MC_VERSION/*, dependencies=Snowmancy.DEPENDENCIES*/)
@EventBusSubscriber
public class Snowmancy
{
	public static final String MODID = "snowmancy";
	public static final String NAME = "Snowmancy";
	public static final String VERSION = "v1.0";
	public static final String MC_VERSION = "1.12";
	public static final String PREFIX = MODID + ":";
	//	public static final String DEPENDENCIES = "";
	//	@SidedProxy(clientSide="bl4ckscor3.mod.snowmancy.proxy.ClientProxy", serverSide="bl4ckscor3.mod.snowmancy.proxy.ServerProxy")
	//	public static ServerProxy proxy;
	@Instance(MODID)
	public static Snowmancy instance;

	@ObjectHolder(PREFIX + BlockSnowmanBuilder.NAME)
	public static Block BUILDER;

	private static final List<ItemBlock> ITEM_BLOCKS_TO_REGISTER = new ArrayList<>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModMetadata modMeta = event.getModMetadata();

		modMeta.authorList = Arrays.asList(new String[] {
				"bl4ckscor3"
		});
		modMeta.autogenerated = false;
		modMeta.description = "Build your own snowman companion!";
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		registerBlock(event, new BlockSnowmanBuilder());

		GameRegistry.registerTileEntity(TileEntitySnowmanBuilder.class, new ResourceLocation(MODID, NAME));
	}

	/**
	 * Registers a block and schedules the registering of its item block
	 * @param event The event holding the registry to register the block to
	 * @param block The block to register
	 */
	private static void registerBlock(RegistryEvent.Register<Block> event, Block block)
	{
		ItemBlock ib = new ItemBlock(block);

		event.getRegistry().register(block);
		ib.setRegistryName(block.getRegistryName());
		ITEM_BLOCKS_TO_REGISTER.add(ib);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		for(ItemBlock ib : ITEM_BLOCKS_TO_REGISTER)
		{
			event.getRegistry().register(ib);
		}
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BUILDER), 0, new ModelResourceLocation(new ResourceLocation(MODID, BlockSnowmanBuilder.NAME), "inventory"));
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
	{
	}

	@SubscribeEvent
	public static void onConfigChanged(OnConfigChangedEvent event)
	{
		if(event.getModID().equals(MODID))
			ConfigManager.sync(MODID, Type.INSTANCE);
	}
}
