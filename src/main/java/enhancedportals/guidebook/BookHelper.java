package enhancedportals.guidebook;

import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class BookHelper
{
    public static void Entry(Map<ResourceLocation, EntryAbstract> section, String localization, Entry entry){
        section.put(new ResourceLocation("enhancedportals", localization), entry);
    }
}
