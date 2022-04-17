package com.minecolonies.coremod.colony.workorders.view;

import com.minecolonies.api.colony.buildings.views.IBuildingView;
import com.minecolonies.api.colony.buildings.workerbuildings.ITownHallView;
import com.minecolonies.api.util.constant.TranslationConstants;
import com.minecolonies.coremod.colony.buildings.workerbuildings.BuildingBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * The client side representation for a work order that the builder can take to build decorations.
 */
public class WorkOrderDecorationView extends AbstractWorkOrderView
{
    @Override
    public Component getDisplayName()
    {
        return getOrderTypePrefix(new TranslatableComponent(getWorkOrderName()));
    }

    private Component getOrderTypePrefix(Component nameComponent)
    {
        switch (this.getWorkOrderType())
        {
            case BUILD:
                return new TranslatableComponent(TranslationConstants.BUILDER_ACTION_BUILDING, nameComponent);
            case UPGRADE:
                return new TranslatableComponent(TranslationConstants.BUILDER_ACTION_UPGRADING, nameComponent, getCurrentLevel(), getTargetLevel());
            case REPAIR:
                return new TranslatableComponent(TranslationConstants.BUILDER_ACTION_REPAIRING, nameComponent);
            case REMOVE:
                return new TranslatableComponent(TranslationConstants.BUILDER_ACTION_REMOVING, nameComponent);
            default:
                return nameComponent;
        }
    }

    @Override
    public boolean shouldShowIn(IBuildingView view)
    {
        return view instanceof ITownHallView || view instanceof BuildingBuilder.View;
    }
}