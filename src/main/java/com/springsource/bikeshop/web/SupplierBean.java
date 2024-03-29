package com.springsource.bikeshop.web;

import com.springsource.bikeshop.domain.Product;
import com.springsource.bikeshop.domain.Supplier;
import com.springsource.bikeshop.domain.SupplierRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.RegexValidator;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.component.spinner.Spinner;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@Configurable
@ManagedBean(name = "supplierBean")
@SessionScoped
@RooSerializable
@RooJsfManagedBean(entity = Supplier.class, beanName = "supplierBean")
public class SupplierBean implements Serializable {

	@Autowired
    SupplierRepository supplierRepository;

	private String name = "Suppliers";

	private Supplier supplier;

	private List<Supplier> allSuppliers;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	private List<Product> selectedProducts;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("name");
        columns.add("address");
        columns.add("description");
        columns.add("supplierNumber");
        columns.add("inceptionDate");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Supplier> getAllSuppliers() {
        return allSuppliers;
    }

	public void setAllSuppliers(List<Supplier> allSuppliers) {
        this.allSuppliers = allSuppliers;
    }

	public String findAllSuppliers() {
        allSuppliers = supplierRepository.findAll();
        dataVisible = !allSuppliers.isEmpty();
        return null;
    }

	public boolean isDataVisible() {
        return dataVisible;
    }

	public void setDataVisible(boolean dataVisible) {
        this.dataVisible = dataVisible;
    }

	public HtmlPanelGrid getCreatePanelGrid() {
        if (createPanelGrid == null) {
            createPanelGrid = populateCreatePanel();
        }
        return createPanelGrid;
    }

	public void setCreatePanelGrid(HtmlPanelGrid createPanelGrid) {
        this.createPanelGrid = createPanelGrid;
    }

	public HtmlPanelGrid getEditPanelGrid() {
        if (editPanelGrid == null) {
            editPanelGrid = populateEditPanel();
        }
        return editPanelGrid;
    }

	public void setEditPanelGrid(HtmlPanelGrid editPanelGrid) {
        this.editPanelGrid = editPanelGrid;
    }

	public HtmlPanelGrid getViewPanelGrid() {
        if (viewPanelGrid == null) {
            viewPanelGrid = populateViewPanel();
        }
        return viewPanelGrid;
    }

	public void setViewPanelGrid(HtmlPanelGrid viewPanelGrid) {
        this.viewPanelGrid = viewPanelGrid;
    }

	public HtmlPanelGrid populateCreatePanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText nameCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nameCreateOutput.setId("nameCreateOutput");
        nameCreateOutput.setValue("Name: * ");
        htmlPanelGrid.getChildren().add(nameCreateOutput);
        
        InputText nameCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        nameCreateInput.setId("nameCreateInput");
        nameCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.name}", String.class));
        LengthValidator nameCreateInputValidator = new LengthValidator();
        nameCreateInputValidator.setMaximum(25);
        nameCreateInput.addValidator(nameCreateInputValidator);
        nameCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nameCreateInput);
        
        Message nameCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nameCreateInputMessage.setId("nameCreateInputMessage");
        nameCreateInputMessage.setFor("nameCreateInput");
        nameCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nameCreateInputMessage);
        
        HtmlOutputText addressCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        addressCreateOutput.setId("addressCreateOutput");
        addressCreateOutput.setValue("Address: * ");
        htmlPanelGrid.getChildren().add(addressCreateOutput);
        
        InputTextarea addressCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        addressCreateInput.setId("addressCreateInput");
        addressCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.address}", String.class));
        LengthValidator addressCreateInputValidator = new LengthValidator();
        addressCreateInputValidator.setMaximum(100);
        addressCreateInput.addValidator(addressCreateInputValidator);
        addressCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(addressCreateInput);
        
        Message addressCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        addressCreateInputMessage.setId("addressCreateInputMessage");
        addressCreateInputMessage.setFor("addressCreateInput");
        addressCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(addressCreateInputMessage);
        
        HtmlOutputText descriptionCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descriptionCreateOutput.setId("descriptionCreateOutput");
        descriptionCreateOutput.setValue("Description:   ");
        htmlPanelGrid.getChildren().add(descriptionCreateOutput);
        
        InputText descriptionCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        descriptionCreateInput.setId("descriptionCreateInput");
        descriptionCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.description}", String.class));
        htmlPanelGrid.getChildren().add(descriptionCreateInput);
        
        Message descriptionCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        descriptionCreateInputMessage.setId("descriptionCreateInputMessage");
        descriptionCreateInputMessage.setFor("descriptionCreateInput");
        descriptionCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(descriptionCreateInputMessage);
        
        HtmlOutputText supplierNumberCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        supplierNumberCreateOutput.setId("supplierNumberCreateOutput");
        supplierNumberCreateOutput.setValue("Supplier Number: * ");
        htmlPanelGrid.getChildren().add(supplierNumberCreateOutput);
        
        Spinner supplierNumberCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        supplierNumberCreateInput.setId("supplierNumberCreateInput");
        supplierNumberCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.supplierNumber}", Integer.class));
        supplierNumberCreateInput.setRequired(true);
        supplierNumberCreateInput.setMin(1.0);
        supplierNumberCreateInput.setMax(99.0);
        LongRangeValidator supplierNumberCreateInputValidator = new LongRangeValidator();
        supplierNumberCreateInputValidator.setMinimum(1);
        supplierNumberCreateInputValidator.setMaximum(99);
        supplierNumberCreateInput.addValidator(supplierNumberCreateInputValidator);
        
        htmlPanelGrid.getChildren().add(supplierNumberCreateInput);
        
        Message supplierNumberCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        supplierNumberCreateInputMessage.setId("supplierNumberCreateInputMessage");
        supplierNumberCreateInputMessage.setFor("supplierNumberCreateInput");
        supplierNumberCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(supplierNumberCreateInputMessage);
        
        HtmlOutputText inceptionDateCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        inceptionDateCreateOutput.setId("inceptionDateCreateOutput");
        inceptionDateCreateOutput.setValue("Inception Date:   ");
        htmlPanelGrid.getChildren().add(inceptionDateCreateOutput);
        
        Calendar inceptionDateCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        inceptionDateCreateInput.setId("inceptionDateCreateInput");
        inceptionDateCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.inceptionDate}", Date.class));
        inceptionDateCreateInput.setNavigator(true);
        inceptionDateCreateInput.setEffect("slideDown");
        inceptionDateCreateInput.setPattern("dd/MM/yyyy");
        inceptionDateCreateInput.setRequired(false);
        inceptionDateCreateInput.setMaxdate(new Date());
        htmlPanelGrid.getChildren().add(inceptionDateCreateInput);
        
        Message inceptionDateCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        inceptionDateCreateInputMessage.setId("inceptionDateCreateInputMessage");
        inceptionDateCreateInputMessage.setFor("inceptionDateCreateInput");
        inceptionDateCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(inceptionDateCreateInputMessage);
        
        HtmlOutputText emailCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        emailCreateOutput.setId("emailCreateOutput");
        emailCreateOutput.setValue("Email:   ");
        htmlPanelGrid.getChildren().add(emailCreateOutput);
        
        InputText emailCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        emailCreateInput.setId("emailCreateInput");
        emailCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.email}", String.class));
        RegexValidator emailCreateInputRegexValidator = new RegexValidator();
        emailCreateInputRegexValidator.setPattern("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
        emailCreateInput.addValidator(emailCreateInputRegexValidator);
        htmlPanelGrid.getChildren().add(emailCreateInput);
        
        Message emailCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        emailCreateInputMessage.setId("emailCreateInputMessage");
        emailCreateInputMessage.setFor("emailCreateInput");
        emailCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(emailCreateInputMessage);
        
        HtmlOutputText productsCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        productsCreateOutput.setId("productsCreateOutput");
        productsCreateOutput.setValue("Products:   ");
        htmlPanelGrid.getChildren().add(productsCreateOutput);
        
        HtmlOutputText productsCreateInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        productsCreateInput.setId("productsCreateInput");
        productsCreateInput.setValue("This relationship is managed from the Product side");
        htmlPanelGrid.getChildren().add(productsCreateInput);
        
        Message productsCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        productsCreateInputMessage.setId("productsCreateInputMessage");
        productsCreateInputMessage.setFor("productsCreateInput");
        productsCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(productsCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText nameEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nameEditOutput.setId("nameEditOutput");
        nameEditOutput.setValue("Name: * ");
        htmlPanelGrid.getChildren().add(nameEditOutput);
        
        InputText nameEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        nameEditInput.setId("nameEditInput");
        nameEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.name}", String.class));
        LengthValidator nameEditInputValidator = new LengthValidator();
        nameEditInputValidator.setMaximum(25);
        nameEditInput.addValidator(nameEditInputValidator);
        nameEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nameEditInput);
        
        Message nameEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nameEditInputMessage.setId("nameEditInputMessage");
        nameEditInputMessage.setFor("nameEditInput");
        nameEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nameEditInputMessage);
        
        HtmlOutputText addressEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        addressEditOutput.setId("addressEditOutput");
        addressEditOutput.setValue("Address: * ");
        htmlPanelGrid.getChildren().add(addressEditOutput);
        
        InputTextarea addressEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        addressEditInput.setId("addressEditInput");
        addressEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.address}", String.class));
        LengthValidator addressEditInputValidator = new LengthValidator();
        addressEditInputValidator.setMaximum(100);
        addressEditInput.addValidator(addressEditInputValidator);
        addressEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(addressEditInput);
        
        Message addressEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        addressEditInputMessage.setId("addressEditInputMessage");
        addressEditInputMessage.setFor("addressEditInput");
        addressEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(addressEditInputMessage);
        
        HtmlOutputText descriptionEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descriptionEditOutput.setId("descriptionEditOutput");
        descriptionEditOutput.setValue("Description:   ");
        htmlPanelGrid.getChildren().add(descriptionEditOutput);
        
        InputText descriptionEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        descriptionEditInput.setId("descriptionEditInput");
        descriptionEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.description}", String.class));
        htmlPanelGrid.getChildren().add(descriptionEditInput);
        
        Message descriptionEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        descriptionEditInputMessage.setId("descriptionEditInputMessage");
        descriptionEditInputMessage.setFor("descriptionEditInput");
        descriptionEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(descriptionEditInputMessage);
        
        HtmlOutputText supplierNumberEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        supplierNumberEditOutput.setId("supplierNumberEditOutput");
        supplierNumberEditOutput.setValue("Supplier Number: * ");
        htmlPanelGrid.getChildren().add(supplierNumberEditOutput);
        
        Spinner supplierNumberEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        supplierNumberEditInput.setId("supplierNumberEditInput");
        supplierNumberEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.supplierNumber}", Integer.class));
        supplierNumberEditInput.setRequired(true);
        supplierNumberEditInput.setMin(1.0);
        supplierNumberEditInput.setMax(99.0);
        LongRangeValidator supplierNumberEditInputValidator = new LongRangeValidator();
        supplierNumberEditInputValidator.setMinimum(1);
        supplierNumberEditInputValidator.setMaximum(99);
        supplierNumberEditInput.addValidator(supplierNumberEditInputValidator);
        
        htmlPanelGrid.getChildren().add(supplierNumberEditInput);
        
        Message supplierNumberEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        supplierNumberEditInputMessage.setId("supplierNumberEditInputMessage");
        supplierNumberEditInputMessage.setFor("supplierNumberEditInput");
        supplierNumberEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(supplierNumberEditInputMessage);
        
        HtmlOutputText inceptionDateEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        inceptionDateEditOutput.setId("inceptionDateEditOutput");
        inceptionDateEditOutput.setValue("Inception Date:   ");
        htmlPanelGrid.getChildren().add(inceptionDateEditOutput);
        
        Calendar inceptionDateEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        inceptionDateEditInput.setId("inceptionDateEditInput");
        inceptionDateEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.inceptionDate}", Date.class));
        inceptionDateEditInput.setNavigator(true);
        inceptionDateEditInput.setEffect("slideDown");
        inceptionDateEditInput.setPattern("dd/MM/yyyy");
        inceptionDateEditInput.setRequired(false);
        inceptionDateEditInput.setMaxdate(new Date());
        htmlPanelGrid.getChildren().add(inceptionDateEditInput);
        
        Message inceptionDateEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        inceptionDateEditInputMessage.setId("inceptionDateEditInputMessage");
        inceptionDateEditInputMessage.setFor("inceptionDateEditInput");
        inceptionDateEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(inceptionDateEditInputMessage);
        
        HtmlOutputText emailEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        emailEditOutput.setId("emailEditOutput");
        emailEditOutput.setValue("Email:   ");
        htmlPanelGrid.getChildren().add(emailEditOutput);
        
        InputText emailEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        emailEditInput.setId("emailEditInput");
        emailEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.email}", String.class));
        RegexValidator emailEditInputRegexValidator = new RegexValidator();
        emailEditInputRegexValidator.setPattern("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
        emailEditInput.addValidator(emailEditInputRegexValidator);
        htmlPanelGrid.getChildren().add(emailEditInput);
        
        Message emailEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        emailEditInputMessage.setId("emailEditInputMessage");
        emailEditInputMessage.setFor("emailEditInput");
        emailEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(emailEditInputMessage);
        
        HtmlOutputText productsEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        productsEditOutput.setId("productsEditOutput");
        productsEditOutput.setValue("Products:   ");
        htmlPanelGrid.getChildren().add(productsEditOutput);
        
        HtmlOutputText productsEditInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        productsEditInput.setId("productsEditInput");
        productsEditInput.setValue("This relationship is managed from the Product side");
        htmlPanelGrid.getChildren().add(productsEditInput);
        
        Message productsEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        productsEditInputMessage.setId("productsEditInputMessage");
        productsEditInputMessage.setFor("productsEditInput");
        productsEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(productsEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText nameLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nameLabel.setId("nameLabel");
        nameLabel.setValue("Name:   ");
        htmlPanelGrid.getChildren().add(nameLabel);
        
        HtmlOutputText nameValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nameValue.setId("nameValue");
        nameValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.name}", String.class));
        htmlPanelGrid.getChildren().add(nameValue);
        
        HtmlOutputText addressLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        addressLabel.setId("addressLabel");
        addressLabel.setValue("Address:   ");
        htmlPanelGrid.getChildren().add(addressLabel);
        
        InputTextarea addressValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        addressValue.setId("addressValue");
        addressValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.address}", String.class));
        addressValue.setReadonly(true);
        addressValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(addressValue);
        
        HtmlOutputText descriptionLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descriptionLabel.setId("descriptionLabel");
        descriptionLabel.setValue("Description:   ");
        htmlPanelGrid.getChildren().add(descriptionLabel);
        
        HtmlOutputText descriptionValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descriptionValue.setId("descriptionValue");
        descriptionValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.description}", String.class));
        htmlPanelGrid.getChildren().add(descriptionValue);
        
        HtmlOutputText supplierNumberLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        supplierNumberLabel.setId("supplierNumberLabel");
        supplierNumberLabel.setValue("Supplier Number:   ");
        htmlPanelGrid.getChildren().add(supplierNumberLabel);
        
        HtmlOutputText supplierNumberValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        supplierNumberValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.supplierNumber}", String.class));
        htmlPanelGrid.getChildren().add(supplierNumberValue);
        
        HtmlOutputText inceptionDateLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        inceptionDateLabel.setId("inceptionDateLabel");
        inceptionDateLabel.setValue("Inception Date:   ");
        htmlPanelGrid.getChildren().add(inceptionDateLabel);
        
        HtmlOutputText inceptionDateValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        inceptionDateValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.inceptionDate}", Date.class));
        DateTimeConverter inceptionDateValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        inceptionDateValueConverter.setPattern("dd/MM/yyyy");
        inceptionDateValue.setConverter(inceptionDateValueConverter);
        htmlPanelGrid.getChildren().add(inceptionDateValue);
        
        HtmlOutputText emailLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        emailLabel.setId("emailLabel");
        emailLabel.setValue("Email:   ");
        htmlPanelGrid.getChildren().add(emailLabel);
        
        HtmlOutputText emailValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        emailValue.setId("emailValue");
        emailValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{supplierBean.supplier.email}", String.class));
        htmlPanelGrid.getChildren().add(emailValue);
        
        HtmlOutputText productsLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        productsLabel.setId("productsLabel");
        productsLabel.setValue("Products:   ");
        htmlPanelGrid.getChildren().add(productsLabel);
        
        HtmlOutputText productsValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        productsValue.setId("productsValue");
        productsValue.setValue("This relationship is managed from the Product side");
        htmlPanelGrid.getChildren().add(productsValue);
        
        return htmlPanelGrid;
    }

	public Supplier getSupplier() {
        if (supplier == null) {
            supplier = new Supplier();
        }
        return supplier;
    }

	public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

	public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

	public void setSelectedProducts(List<Product> selectedProducts) {
        if (selectedProducts != null) {
            supplier.setProducts(new HashSet<Product>(selectedProducts));
        }
        this.selectedProducts = selectedProducts;
    }

	public String onEdit() {
        if (supplier != null && supplier.getProducts() != null) {
            selectedProducts = new ArrayList<Product>(supplier.getProducts());
        }
        return null;
    }

	public boolean isCreateDialogVisible() {
        return createDialogVisible;
    }

	public void setCreateDialogVisible(boolean createDialogVisible) {
        this.createDialogVisible = createDialogVisible;
    }

	public String displayList() {
        createDialogVisible = false;
        findAllSuppliers();
        return "supplier";
    }

	public String displayCreateDialog() {
        supplier = new Supplier();
        createDialogVisible = true;
        return "supplier";
    }

	public String persist() {
        String message = "";
        if (supplier.getId() != null) {
            supplierRepository.save(supplier);
            message = "Successfully updated";
        } else {
            supplierRepository.save(supplier);
            message = "Successfully created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialog.hide()");
        context.execute("editDialog.hide()");
        
        FacesMessage facesMessage = new FacesMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllSuppliers();
    }

	public String delete() {
        supplierRepository.delete(supplier);
        FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllSuppliers();
    }

	public void reset() {
        supplier = null;
        selectedProducts = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	private static final long serialVersionUID = 1L;
}
