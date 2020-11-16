package examsystem.exsys.Views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "contact", layout = MainTemplateView.class)
@PageTitle("Kapcsolat")
@CssImport("styles/views/kapcsolat/kapcsolat-view.css")
public class ContactView extends Div {

    public ContactView() {
        setId("kapcsolat-view");
        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();

        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setAlignItems(FlexComponent.Alignment.CENTER);

        container.add(wrapper);

        createTitle(wrapper, "Kapcsolat");
        createLabel(wrapper, "Ha akármilyen problémája, kérdése merül fel, akkor érdeklődjön az alább megadott elérhetőségeken.");
        createLabel(wrapper,"Email: info@exsys.hu");
        createLabel(wrapper,"Telefon: +36 30 556 8315");

        wrapper.setWidth("90%");
        wrapper.setPadding(false);
        add(container);
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
        wrapper.add(h1);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(true);
        wrapper.setPadding(true);
        return wrapper;
    }

    private void createLabel(VerticalLayout wrapper, String text){
        Label label = new Label(text);
        label.getElement().getStyle()
                .set("font-size", "18px");
        wrapper.add(label);
    }

}
