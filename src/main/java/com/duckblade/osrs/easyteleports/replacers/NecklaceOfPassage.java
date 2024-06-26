package com.duckblade.osrs.easyteleports.replacers;

import com.duckblade.osrs.easyteleports.EasyTeleportsConfig;
import com.duckblade.osrs.easyteleports.TeleportReplacement;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.widgets.Widget;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class NecklaceOfPassage implements Replacer
{

	private static final Set<String> TALISMAN_ADVENTURE_LOG_HEADER_PREFIXES = ImmutableSet.of(
		"The necklace has",
		"Necklace of Passage teleports"
	);

	private final List<TeleportReplacement> replacements = new ArrayList<>(3);

	@Getter(onMethod = @__(@Override))
	private boolean enabled = false;

	@Override
	public void onConfigChanged(EasyTeleportsConfig config)
	{
		this.enabled = config.enableNecklaceOfPassage();

		replacements.clear();
		replacements.add(new TeleportReplacement("Wizards' Tower", config.replacementWizardsTower()));
		replacements.add(new TeleportReplacement("The Outpost", config.replacementOutpost()));
		replacements.add(new TeleportReplacement("Eagle's Eyrie", config.replacementEagleEyrie()));
	}

	@Override
	public List<TeleportReplacement> getReplacements()
	{
		return ImmutableList.copyOf(replacements);
	}

	@Override
	public boolean isApplicableToAdventureLog(Widget root)
	{
		return root != null &&
			root.getText() != null &&
			TALISMAN_ADVENTURE_LOG_HEADER_PREFIXES.stream()
				.anyMatch(s -> root.getText().startsWith(s));
	}

	@Override
	public EquipmentInventorySlot getEquipmentSlot()
	{
		return EquipmentInventorySlot.AMULET;
	}
}
