package net.datasa.Gakusei_Kanri.service;

import lombok.RequiredArgsConstructor;
import net.datasa.Gakusei_Kanri.domain.dto.StudentDTO;
import net.datasa.Gakusei_Kanri.domain.dto.StudentForm;
import net.datasa.Gakusei_Kanri.domain.entity.StudentEntity;
import net.datasa.Gakusei_Kanri.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentDTO create(StudentForm form) {
        StudentEntity entity = StudentEntity.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .phone(form.getPhone())
                .birthYear(form.getBirthYear())
                .active(form.isActive())
                .build();
        StudentEntity saved = studentRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public StudentDTO findOne(Long id) {
        StudentEntity entity = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        return toDTO(entity);
    }

    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO update(Long id, StudentForm form) {
        StudentEntity entity = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        entity.setFirstName(form.getFirstName());
        entity.setLastName(form.getLastName());
        entity.setEmail(form.getEmail());
        entity.setPhone(form.getPhone());
        entity.setBirthYear(form.getBirthYear());
        entity.setActive(form.isActive());

        return toDTO(studentRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        StudentEntity entity = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        studentRepository.delete(entity);
    }

    @Override
    public StudentForm getForm(Long id) {
        StudentEntity entity = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        StudentForm form = new StudentForm();
        form.setFirstName(entity.getFirstName());
        form.setLastName(entity.getLastName());
        form.setEmail(entity.getEmail());
        form.setPhone(entity.getPhone());
        form.setBirthYear(entity.getBirthYear());
        form.setActive(entity.isActive());
        return form;
    }

    private StudentDTO toDTO(StudentEntity entity) {
        String status = entity.isActive() ? "ACTIVE" : "INACTIVE";
        String fullName = String.format("%s %s", entity.getFirstName(), entity.getLastName());
        String maskedPhone = StudentDTO.maskPhone(entity.getPhone());
        return new StudentDTO(
                entity.getId(),
                fullName,
                maskedPhone,
                status,
                entity.getBirthYear(),
                entity.getEmail()
        );
    }
}
