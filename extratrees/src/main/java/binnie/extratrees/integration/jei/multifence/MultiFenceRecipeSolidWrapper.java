package binnie.extratrees.integration.jei.multifence;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import binnie.extratrees.wood.planks.IPlankType;
import binnie.extratrees.wood.WoodManager;
import binnie.extratrees.blocks.decor.FenceDescription;
import binnie.extratrees.blocks.decor.FenceType;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;

public class MultiFenceRecipeSolidWrapper implements IShapedCraftingRecipeWrapper {

	private final FenceType fenceType;
	@Nullable
	private final IPlankType plankType;
	@Nullable
	private final IPlankType plankTypeSecondary;

	public MultiFenceRecipeSolidWrapper(FenceType fenceType) {
		this.fenceType = fenceType;
		this.plankType = null;
		this.plankTypeSecondary = null;
	}

	public MultiFenceRecipeSolidWrapper(FenceDescription fenceDesc) {
		this.fenceType = fenceDesc.getFenceType();
		this.plankType = fenceDesc.getPlankType();
		this.plankTypeSecondary = fenceDesc.getSecondaryPlankType();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> fences = new ArrayList<>();
		List<ItemStack> outputFences = new ArrayList<>();
		if (plankType != null) {
			if (plankTypeSecondary == null) {
				for (IPlankType plankTypeSecondary : WoodManager.getAllPlankTypes()) {
					if (plankType != plankTypeSecondary) {
						fences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.getSize(), false, fenceType.isEmbossed()), 1));
						outputFences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.getSize(), true, fenceType.isEmbossed()), 2));
					}
				}
			} else {
				fences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.getSize(), false, fenceType.isEmbossed()), 1));
				outputFences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.getSize(), true, fenceType.isEmbossed()), 2));
			}
		} else {
			for (IPlankType plankTypeSecondary : WoodManager.getAllPlankTypes()) {
				for (IPlankType plankType : WoodManager.getAllPlankTypes()) {
					if (plankType != plankTypeSecondary) {
						fences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.getSize(), false, fenceType.isEmbossed()), 1));
						outputFences.add(WoodManager.getFence(plankType, plankTypeSecondary, new FenceType(fenceType.getSize(), true, fenceType.isEmbossed()), 2));
					}
				}
			}
		}
		List<List<ItemStack>> itemInputs = new ArrayList<>();
		itemInputs.add(fences);
		itemInputs.add(fences);
		itemInputs.add(fences);
		List<List<ItemStack>> itemOutputs = new ArrayList<>();
		itemOutputs.add(outputFences);
		ingredients.setOutputLists(VanillaTypes.ITEM, itemOutputs);
		ingredients.setInputLists(VanillaTypes.ITEM, itemInputs);
	}

	@Override
	public int getWidth() {
		return 3;
	}

	@Override
	public int getHeight() {
		return 1;
	}
}
