package com.springsource.bikeshop.web;

import com.springsource.bikeshop.domain.Product;
import com.springsource.bikeshop.domain.ProductRepository;
import com.springsource.bikeshop.domain.Supplier;
import com.springsource.bikeshop.domain.SupplierRepository;
import com.springsource.bikeshop.reference.ProductType;
import com.springsource.bikeshop.web.converter.SupplierConverter;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.LengthValidator;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.filedownload.FileDownloadActionListener;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@ManagedBean(name = "productBean")
@SessionScoped
@Configurable
@RooSerializable
@RooJsfManagedBean(entity = Product.class, beanName = "productBean")
public class ProductBean implements Serializable {

	@Autowired
    ProductRepository productRepository;

	@Autowired
    SupplierRepository supplierRepository;

	private String name = "Products";

	private Product product;

	private List<Product> allProducts;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("name");
        columns.add("description");
        columns.add("releaseDate");
        columns.add("weight");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Product> getAllProducts() {
        return allProducts;
    }

	public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }

	public String findAllProducts() {
        allProducts = productRepository.findAll();
        dataVisible = !allProducts.isEmpty();
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
        nameCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.name}", String.class));
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
        
        HtmlOutputText descriptionCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descriptionCreateOutput.setId("descriptionCreateOutput");
        descriptionCreateOutput.setValue("Description: * ");
        htmlPanelGrid.getChildren().add(descriptionCreateOutput);
        
        InputTextarea descriptionCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        descriptionCreateInput.setId("descriptionCreateInput");
        descriptionCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.description}", String.class));
        LengthValidator descriptionCreateInputValidator = new LengthValidator();
        descriptionCreateInputValidator.setMaximum(250);
        descriptionCreateInput.addValidator(descriptionCreateInputValidator);
        descriptionCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(descriptionCreateInput);
        
        Message descriptionCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        descriptionCreateInputMessage.setId("descriptionCreateInputMessage");
        descriptionCreateInputMessage.setFor("descriptionCreateInput");
        descriptionCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(descriptionCreateInputMessage);
        
        HtmlOutputText productTypeCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        productTypeCreateOutput.setId("productTypeCreateOutput");
        productTypeCreateOutput.setValue("Product Type: * ");
        htmlPanelGrid.getChildren().add(productTypeCreateOutput);
        
        AutoComplete productTypeCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        productTypeCreateInput.setId("productTypeCreateInput");
        productTypeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.productType}", ProductType.class));
        productTypeCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{productBean.completeProductType}", List.class, new Class[] { String.class }));
        productTypeCreateInput.setDropdown(true);
        productTypeCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(productTypeCreateInput);
        
        Message productTypeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        productTypeCreateInputMessage.setId("productTypeCreateInputMessage");
        productTypeCreateInputMessage.setFor("productTypeCreateInput");
        productTypeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(productTypeCreateInputMessage);
        
        HtmlOutputText releaseDateCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        releaseDateCreateOutput.setId("releaseDateCreateOutput");
        releaseDateCreateOutput.setValue("Release Date:   ");
        htmlPanelGrid.getChildren().add(releaseDateCreateOutput);
        
        Calendar releaseDateCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        releaseDateCreateInput.setId("releaseDateCreateInput");
        releaseDateCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.releaseDate}", Date.class));
        releaseDateCreateInput.setNavigator(true);
        releaseDateCreateInput.setEffect("slideDown");
        releaseDateCreateInput.setPattern("dd/MM/yyyy");
        releaseDateCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(releaseDateCreateInput);
        
        Message releaseDateCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        releaseDateCreateInputMessage.setId("releaseDateCreateInputMessage");
        releaseDateCreateInputMessage.setFor("releaseDateCreateInput");
        releaseDateCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(releaseDateCreateInputMessage);
        
        HtmlOutputText weightCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        weightCreateOutput.setId("weightCreateOutput");
        weightCreateOutput.setValue("Weight: * ");
        htmlPanelGrid.getChildren().add(weightCreateOutput);
        
        InputText weightCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        weightCreateInput.setId("weightCreateInput");
        weightCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.weight}", BigDecimal.class));
        weightCreateInput.setRequired(true);
        DoubleRangeValidator weightCreateInputValidator = new DoubleRangeValidator();
        weightCreateInputValidator.setMinimum(0.0);
        weightCreateInputValidator.setMaximum(9.99);
        weightCreateInput.addValidator(weightCreateInputValidator);
        htmlPanelGrid.getChildren().add(weightCreateInput);
        
        Message weightCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        weightCreateInputMessage.setId("weightCreateInputMessage");
        weightCreateInputMessage.setFor("weightCreateInput");
        weightCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(weightCreateInputMessage);
        
        HtmlOutputText imageCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        imageCreateOutput.setId("imageCreateOutput");
        imageCreateOutput.setValue("Image:   ");
        htmlPanelGrid.getChildren().add(imageCreateOutput);
        
        FileUpload imageCreateInput = (FileUpload) application.createComponent(FileUpload.COMPONENT_TYPE);
        imageCreateInput.setId("imageCreateInput");
        imageCreateInput.setFileUploadListener(expressionFactory.createMethodExpression(elContext, "#{productBean.handleFileUploadForImage}", void.class, new Class[] { FileUploadEvent.class }));
        imageCreateInput.setMode("advanced");
        imageCreateInput.setAllowTypes("/(\\.|\\/)([jJ][pP][gG]|[jJ][pP][eE][gG])$/");
        imageCreateInput.setUpdate(":growlForm:growl");
        htmlPanelGrid.getChildren().add(imageCreateInput);
        
        Message imageCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        imageCreateInputMessage.setId("imageCreateInputMessage");
        imageCreateInputMessage.setFor("imageCreateInput");
        imageCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(imageCreateInputMessage);
        
        HtmlOutputText supplierCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        supplierCreateOutput.setId("supplierCreateOutput");
        supplierCreateOutput.setValue("Supplier: * ");
        htmlPanelGrid.getChildren().add(supplierCreateOutput);
        
        AutoComplete supplierCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        supplierCreateInput.setId("supplierCreateInput");
        supplierCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.supplier}", Supplier.class));
        supplierCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{productBean.completeSupplier}", List.class, new Class[] { String.class }));
        supplierCreateInput.setDropdown(true);
        supplierCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "supplier", String.class));
        supplierCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{supplier.name} #{supplier.address} #{supplier.description} #{supplier.supplierNumber}", String.class));
        supplierCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{supplier}", Supplier.class));
        supplierCreateInput.setConverter(new SupplierConverter());
        supplierCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(supplierCreateInput);
        
        Message supplierCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        supplierCreateInputMessage.setId("supplierCreateInputMessage");
        supplierCreateInputMessage.setFor("supplierCreateInput");
        supplierCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(supplierCreateInputMessage);
        
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
        nameEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.name}", String.class));
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
        
        HtmlOutputText descriptionEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descriptionEditOutput.setId("descriptionEditOutput");
        descriptionEditOutput.setValue("Description: * ");
        htmlPanelGrid.getChildren().add(descriptionEditOutput);
        
        InputTextarea descriptionEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        descriptionEditInput.setId("descriptionEditInput");
        descriptionEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.description}", String.class));
        LengthValidator descriptionEditInputValidator = new LengthValidator();
        descriptionEditInputValidator.setMaximum(250);
        descriptionEditInput.addValidator(descriptionEditInputValidator);
        descriptionEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(descriptionEditInput);
        
        Message descriptionEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        descriptionEditInputMessage.setId("descriptionEditInputMessage");
        descriptionEditInputMessage.setFor("descriptionEditInput");
        descriptionEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(descriptionEditInputMessage);
        
        HtmlOutputText productTypeEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        productTypeEditOutput.setId("productTypeEditOutput");
        productTypeEditOutput.setValue("Product Type: * ");
        htmlPanelGrid.getChildren().add(productTypeEditOutput);
        
        AutoComplete productTypeEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        productTypeEditInput.setId("productTypeEditInput");
        productTypeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.productType}", ProductType.class));
        productTypeEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{productBean.completeProductType}", List.class, new Class[] { String.class }));
        productTypeEditInput.setDropdown(true);
        productTypeEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(productTypeEditInput);
        
        Message productTypeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        productTypeEditInputMessage.setId("productTypeEditInputMessage");
        productTypeEditInputMessage.setFor("productTypeEditInput");
        productTypeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(productTypeEditInputMessage);
        
        HtmlOutputText releaseDateEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        releaseDateEditOutput.setId("releaseDateEditOutput");
        releaseDateEditOutput.setValue("Release Date:   ");
        htmlPanelGrid.getChildren().add(releaseDateEditOutput);
        
        Calendar releaseDateEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        releaseDateEditInput.setId("releaseDateEditInput");
        releaseDateEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.releaseDate}", Date.class));
        releaseDateEditInput.setNavigator(true);
        releaseDateEditInput.setEffect("slideDown");
        releaseDateEditInput.setPattern("dd/MM/yyyy");
        releaseDateEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(releaseDateEditInput);
        
        Message releaseDateEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        releaseDateEditInputMessage.setId("releaseDateEditInputMessage");
        releaseDateEditInputMessage.setFor("releaseDateEditInput");
        releaseDateEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(releaseDateEditInputMessage);
        
        HtmlOutputText weightEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        weightEditOutput.setId("weightEditOutput");
        weightEditOutput.setValue("Weight: * ");
        htmlPanelGrid.getChildren().add(weightEditOutput);
        
        InputText weightEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        weightEditInput.setId("weightEditInput");
        weightEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.weight}", BigDecimal.class));
        weightEditInput.setRequired(true);
        DoubleRangeValidator weightEditInputValidator = new DoubleRangeValidator();
        weightEditInputValidator.setMinimum(0.0);
        weightEditInputValidator.setMaximum(9.99);
        weightEditInput.addValidator(weightEditInputValidator);
        htmlPanelGrid.getChildren().add(weightEditInput);
        
        Message weightEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        weightEditInputMessage.setId("weightEditInputMessage");
        weightEditInputMessage.setFor("weightEditInput");
        weightEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(weightEditInputMessage);
        
        HtmlOutputText imageEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        imageEditOutput.setId("imageEditOutput");
        imageEditOutput.setValue("Image:   ");
        htmlPanelGrid.getChildren().add(imageEditOutput);
        
        FileUpload imageEditInput = (FileUpload) application.createComponent(FileUpload.COMPONENT_TYPE);
        imageEditInput.setId("imageEditInput");
        imageEditInput.setFileUploadListener(expressionFactory.createMethodExpression(elContext, "#{productBean.handleFileUploadForImage}", void.class, new Class[] { FileUploadEvent.class }));
        imageEditInput.setMode("advanced");
        imageEditInput.setAllowTypes("/(\\.|\\/)([jJ][pP][gG]|[jJ][pP][eE][gG])$/");
        imageEditInput.setUpdate(":growlForm:growl");
        htmlPanelGrid.getChildren().add(imageEditInput);
        
        Message imageEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        imageEditInputMessage.setId("imageEditInputMessage");
        imageEditInputMessage.setFor("imageEditInput");
        imageEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(imageEditInputMessage);
        
        HtmlOutputText supplierEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        supplierEditOutput.setId("supplierEditOutput");
        supplierEditOutput.setValue("Supplier: * ");
        htmlPanelGrid.getChildren().add(supplierEditOutput);
        
        AutoComplete supplierEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        supplierEditInput.setId("supplierEditInput");
        supplierEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.supplier}", Supplier.class));
        supplierEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{productBean.completeSupplier}", List.class, new Class[] { String.class }));
        supplierEditInput.setDropdown(true);
        supplierEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "supplier", String.class));
        supplierEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{supplier.name} #{supplier.address} #{supplier.description} #{supplier.supplierNumber}", String.class));
        supplierEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{supplier}", Supplier.class));
        supplierEditInput.setConverter(new SupplierConverter());
        supplierEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(supplierEditInput);
        
        Message supplierEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        supplierEditInputMessage.setId("supplierEditInputMessage");
        supplierEditInputMessage.setFor("supplierEditInput");
        supplierEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(supplierEditInputMessage);
        
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
        nameValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.name}", String.class));
        htmlPanelGrid.getChildren().add(nameValue);
        
        HtmlOutputText descriptionLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descriptionLabel.setId("descriptionLabel");
        descriptionLabel.setValue("Description:   ");
        htmlPanelGrid.getChildren().add(descriptionLabel);
        
        InputTextarea descriptionValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        descriptionValue.setId("descriptionValue");
        descriptionValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.description}", String.class));
        descriptionValue.setReadonly(true);
        descriptionValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(descriptionValue);
        
        HtmlOutputText productTypeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        productTypeLabel.setId("productTypeLabel");
        productTypeLabel.setValue("Product Type:   ");
        htmlPanelGrid.getChildren().add(productTypeLabel);
        
        HtmlOutputText productTypeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        productTypeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.productType}", String.class));
        htmlPanelGrid.getChildren().add(productTypeValue);
        
        HtmlOutputText releaseDateLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        releaseDateLabel.setId("releaseDateLabel");
        releaseDateLabel.setValue("Release Date:   ");
        htmlPanelGrid.getChildren().add(releaseDateLabel);
        
        HtmlOutputText releaseDateValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        releaseDateValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.releaseDate}", Date.class));
        DateTimeConverter releaseDateValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        releaseDateValueConverter.setPattern("dd/MM/yyyy");
        releaseDateValue.setConverter(releaseDateValueConverter);
        htmlPanelGrid.getChildren().add(releaseDateValue);
        
        HtmlOutputText weightLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        weightLabel.setId("weightLabel");
        weightLabel.setValue("Weight:   ");
        htmlPanelGrid.getChildren().add(weightLabel);
        
        HtmlOutputText weightValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        weightValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.weight}", String.class));
        htmlPanelGrid.getChildren().add(weightValue);
        
        HtmlOutputText imageLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        imageLabel.setId("imageLabel");
        imageLabel.setValue("Image:   ");
        htmlPanelGrid.getChildren().add(imageLabel);
        
        UIComponent imageValue;
        if (product != null && product.getImage() != null && product.getImage().length > 0) {
            imageValue = (CommandButton) application.createComponent(CommandButton.COMPONENT_TYPE);
            ((CommandButton) imageValue).addActionListener(new FileDownloadActionListener(expressionFactory.createValueExpression(elContext, "#{productBean.imageStreamedContent}", StreamedContent.class), null));
            ((CommandButton) imageValue).setValue("Download");
            ((CommandButton) imageValue).setAjax(false);
        } else {
            imageValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
            ((HtmlOutputText) imageValue).setValue("");
        }
        htmlPanelGrid.getChildren().add(imageValue);
        
        HtmlOutputText supplierLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        supplierLabel.setId("supplierLabel");
        supplierLabel.setValue("Supplier:   ");
        htmlPanelGrid.getChildren().add(supplierLabel);
        
        HtmlOutputText supplierValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        supplierValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{productBean.product.supplier}", Supplier.class));
        supplierValue.setConverter(new SupplierConverter());
        htmlPanelGrid.getChildren().add(supplierValue);
        
        return htmlPanelGrid;
    }

	public Product getProduct() {
        if (product == null) {
            product = new Product();
        }
        return product;
    }

	public void setProduct(Product product) {
        this.product = product;
    }

	public List<ProductType> completeProductType(String query) {
        List<ProductType> suggestions = new ArrayList<ProductType>();
        for (ProductType productType : ProductType.values()) {
            if (productType.name().toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(productType);
            }
        }
        return suggestions;
    }

	public void handleFileUploadForImage(FileUploadEvent event) {
        product.setImage(event.getFile().getContents());
        FacesMessage facesMessage = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

	public StreamedContent getImageStreamedContent() {
        if (product != null && product.getImage() != null) {
            return new DefaultStreamedContent(new ByteArrayInputStream(product.getImage()), "image/jpeg", "image.jpg");
        }
        return new DefaultStreamedContent(new ByteArrayInputStream("".getBytes()));
    }

	public List<Supplier> completeSupplier(String query) {
        List<Supplier> suggestions = new ArrayList<Supplier>();
        for (Supplier supplier : supplierRepository.findAll()) {
            String supplierStr = String.valueOf(supplier.getName() +  " "  + supplier.getAddress() +  " "  + supplier.getDescription() +  " "  + supplier.getSupplierNumber());
            if (supplierStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(supplier);
            }
        }
        return suggestions;
    }

	public String onEdit() {
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
        findAllProducts();
        return "product";
    }

	public String displayCreateDialog() {
        product = new Product();
        createDialogVisible = true;
        return "product";
    }

	public String persist() {
        String message = "";
        if (product.getId() != null) {
            productRepository.save(product);
            message = "Successfully updated";
        } else {
            productRepository.save(product);
            message = "Successfully created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialog.hide()");
        context.execute("editDialog.hide()");
        
        FacesMessage facesMessage = new FacesMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllProducts();
    }

	public String delete() {
        productRepository.delete(product);
        FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllProducts();
    }

	public void reset() {
        product = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	private static final long serialVersionUID = 1L;
}
