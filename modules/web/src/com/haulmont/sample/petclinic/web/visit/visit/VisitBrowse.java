package com.haulmont.sample.petclinic.web.visit.visit;

import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.pet.Pet;
import com.haulmont.sample.petclinic.entity.visit.Visit;

import javax.inject.Inject;

@UiController("petclinic_Visit.browse")
@UiDescriptor("visit-browse.xml")
@LookupComponent("visitsTable")
@LoadDataBeforeShow
public class VisitBrowse extends StandardLookup<Visit> {


    @Inject
    protected MetadataTools metadataTools;
    @Inject
    protected Notifications notifications;

    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private GroupTable<Visit> visitsTable;

    @Inject
    private MessageBundle messageBundle;



    @Subscribe("visitsTable.createRegularCheckup")
    public void createForPet(Action.ActionPerformedEvent event) {
        screenBuilders.lookup(Pet.class, this)
                .withSelectHandler(pets -> {
                    createVisitForPet(pets.iterator().next());
                })
                .withLaunchMode(OpenMode.DIALOG)
                .build()
                .show();
    }

    private void createVisitForPet(Pet pet) {
        screenBuilders.editor(visitsTable)
                .newEntity()
                .withInitializer(visit -> {
                    visit.setDescription(regularCheckupDescriptionContent(pet));
                    visit.setPet(pet);
                })
                .withScreenClass(RegularCheckup.class)
                .withLaunchMode(OpenMode.DIALOG)
                .withAfterCloseListener(event -> showRegularCheckupCreatedNotification(pet))
                .build()
                .show();
    }

    private String regularCheckupDescriptionContent(Pet pet) {
        return messageBundle.formatMessage("regularCheckupContent",
                pet.getName(),
                pet.getIdentificationNumber()
        );
    }

    private void showRegularCheckupCreatedNotification(Pet pet) {

        String petName = metadataTools.getInstanceName(pet);
        String regularCheckupCreatedMessage = messageBundle.formatMessage("regularCheckupCreated", petName);

        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption(regularCheckupCreatedMessage)
                .show();
    }


}