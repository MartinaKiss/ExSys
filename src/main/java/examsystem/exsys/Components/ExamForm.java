package examsystem.exsys.Components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import examsystem.exsys.ExamElements.Exam;
import examsystem.exsys.Repositories.ExamRepository;
import examsystem.exsys.Views.ViewComponents.Reloader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class ExamForm extends VerticalLayout {

    @Autowired
    private ExamRepository examRepository;
    private Exam exam;
    private Reloader reloader;
    private Binder<Exam> binder;

    @PostConstruct
    public void init(){
        binder = new Binder<>(Exam.class);

        Button deleteButton = new Button();
        deleteButton.setText("Delete");
        deleteButton.setIcon(VaadinIcon.TRASH.create());
        deleteButton.addClickListener(buttonClickEvent -> {
            try {
                examRepository.delete(exam);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                reloader.reload();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Notification.show("Successfully removed!");
            setVisible(false);
        });

        Button editButton = new Button();
        editButton.setText("Save");
        editButton.setIcon(VaadinIcon.PENCIL.create());
        editButton.addClickListener(buttonClickEvent -> {
            try {
                if (exam.getExamId() == 0) {
                    examRepository.save(exam);

                } else {
                    examRepository.update(exam);

                }
                setVisible(false);
                reloader.reload();
                Notification.show("Success!");
            }catch (Exception e) {
                Notification.show(":(");
                e.printStackTrace();
            }
            Notification.show("Successfully edited!");
            setVisible(false);
        });

        add(deleteButton);
        add(editButton);
        setVisible(false);
        binder.bindInstanceFields(this);
    }


    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        if(exam!=null){
            this.exam = exam;
            binder.setBean(this.exam);
        }
        this.exam = exam;
    }

    public Reloader getReloader() {
        return reloader;
    }

    public void setReloader(Reloader reloader) {
        this.reloader = reloader;
    }

    public void initSave() {
        exam = new Exam();
        binder.setBean(exam);
        setVisible(true);
    }

    public void initEdit(int id) {

        try {
            this.exam = examRepository.findById(id);
            binder.setBean(exam);
            setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
