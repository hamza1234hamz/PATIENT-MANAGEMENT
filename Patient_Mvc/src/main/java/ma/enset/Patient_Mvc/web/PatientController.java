package ma.enset.Patient_Mvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.Patient_Mvc.entities.Patient;
import ma.enset.Patient_Mvc.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping("/user/index")
    public String patients(Model model,@RequestParam(name="page", defaultValue = "0") int page,
                           @RequestParam(name="size",defaultValue = "4") int size,
                           @RequestParam(name="keyword",defaultValue = "") String keyword){
        Page<Patient> PagePatients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listePatient",PagePatients.getContent());
        model.addAttribute("pages",new int[PagePatients.getTotalPages()]);//permet d'avir un table des pages pour vous puisssent consulter chaque page
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "Patients";
    }
    @GetMapping("/admin/delete")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }
    @GetMapping("/admin/formPatient")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatient";
    }

    @PostMapping(path = "/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "0") int page){
        if(bindingResult.hasErrors()) return "formPatient";
        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
        // redirection : permet de vous placer vers une nouvaeu formulire
    }
    @GetMapping("/admin/editPatient")
    public String editPatient(Model model,Long id,String keyword,int page){
        Patient patient=patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient introuvable ");
        model.addAttribute("patient",patient);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "editPatient";
    }
}
