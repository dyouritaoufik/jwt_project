package ma.formation.jwt_project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ma.formation.jwt_project.dao.EmpRepository;
import ma.formation.jwt_project.domaine.EmpConverter;
import ma.formation.jwt_project.domaine.EmpVo;
import ma.formation.jwt_project.service.model.Emp;
@Service
@Transactional
public class EmpServiceImpl implements IEmpService {
    @Autowired
    private EmpRepository empRepository; @Override
    public List<EmpVo> getEmployees() {
        List<Emp> list = empRepository.findAll();
        return EmpConverter.toListVo(list);
    } @Override
    public void save(EmpVo emp) {
        empRepository.save(EmpConverter.toBo(emp));
    } @Override
    public EmpVo getEmpById(Long id) {
        boolean trouve = empRepository.existsById(id);
        if (!trouve)
            return null;
        return EmpConverter.toVo(empRepository.getById(id));
    } @Override
    public void delete(Long id) {empRepository.deleteById(id);
    } @Override
    public List<EmpVo> findBySalary(Double salaty) {
        List<Emp> list = empRepository.findBySalary(salaty);
        return EmpConverter.toListVo(list);
    } @Override
    public List<EmpVo> findByFonction(String fonction) {
        List<Emp> list = empRepository.findByFonction(fonction);
        return EmpConverter.toListVo(list);
    } @Override
    public List<EmpVo> findBySalaryAndFonction(Double salary, String fonction) {
        List<Emp> list = empRepository.findBySalaryAndFonction(salary, fonction);
        return EmpConverter.toListVo(list);
    } @Override
    public EmpVo getEmpHavaingMaxSalary() {
        return EmpConverter.toVo(empRepository.getEmpHavaingMaxSalary());
    } @Override
    public List<EmpVo> findAll(int pageId, int size) {
        Page<Emp> result = empRepository.findAll(PageRequest.of(pageId, size, Direction.ASC, "name"));
        return EmpConverter.toListVo(result.getContent());
    } @Override
    public List<EmpVo> sortBy(String fieldName) {
        return EmpConverter.toListVo(empRepository.findAll(Sort.by(fieldName)));
    }
}