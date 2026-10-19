package vectorwing.farmersdelight.common.crafting.ingredient;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jspecify.annotations.Nullable;
import vectorwing.farmersdelight.common.registry.ModIngredientTypes;
import vectorwing.farmersdelight.refabricated.ItemAbility;

import java.util.List;
import java.util.stream.Stream;

public class ItemAbilityIngredient implements ICustomIngredient
{
	public static final MapCodec<ItemAbilityIngredient> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
			ItemAbility.CODEC.fieldOf("action").forGetter(ItemAbilityIngredient::getItemAbility)
	).apply(inst, ItemAbilityIngredient::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, ItemAbilityIngredient> STREAM_CODEC = ByteBufCodecs.fromCodec(ItemAbility.CODEC)
			.map(ItemAbilityIngredient::new, ItemAbilityIngredient::getItemAbility).cast();

	protected final ItemAbility itemAbility;
	protected List<Holder<Item>> itemStacks;

	public ItemAbilityIngredient(ItemAbility itemAbility) {
		this.itemAbility = itemAbility;
	}

	protected void dissolve() {
		if (this.itemStacks == null) {
			itemStacks = BuiltInRegistries.ITEM.stream()
					.map(BuiltInRegistries.ITEM::wrapAsHolder)
					.filter(itemAbility::canPerformAction)
					.toList();
		}
	}

	@Override
	public boolean test(@Nullable ItemStack stack) {
		return stack != null && itemAbility.canPerformAction(stack.typeHolder());
	}

	@Override
	public Stream<Holder<Item>> items() {
		dissolve();
		return itemStacks.stream();
	}

	@Override
	public boolean isSimple() {
		return true;
	}

	public ItemAbility getItemAbility() {
		return itemAbility;
	}

	@Override
	public IngredientType<?> getType() {
		return ModIngredientTypes.ITEM_ABILITY.get();
	}
}
