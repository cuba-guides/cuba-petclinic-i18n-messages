package com.haulmont.sample.petclinic.web.visit.visit;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.visit.Visit;


@UiController("petclinic_Visit.regularCheckup")
@UiDescriptor("regular-checkup.xml")
@EditedEntityContainer("visitDc")
@LoadDataBeforeShow
public class RegularCheckup extends StandardEditor<Visit> {

}