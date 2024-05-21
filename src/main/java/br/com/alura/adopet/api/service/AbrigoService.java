package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoResponseDto;
import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.dto.PetRequestDto;
import br.com.alura.adopet.api.dto.PetResponseDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbrigoService {
    final AbrigoRepository repository;

    final PetRepository petRepository;

    public AbrigoService(AbrigoRepository repository, PetRepository petRepository) {
        this.repository = repository;
        this.petRepository = petRepository;
    }

    public void cadastrar(CadastrarAbrigoDto dto){
        boolean dadosJaCadastrados = repository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email());

        if (dadosJaCadastrados) {
            throw new ValidacaoException("Abrigo já cadastrado");
        }
        Abrigo abrigo = new Abrigo(dto);
        repository.save(abrigo);
    }

    public List<PetResponseDto> listarPets(String idOuNome){
        Abrigo abrigo = buscarAbrigo(idOuNome);

        return petRepository.findByAbrigo(abrigo).stream().map(PetResponseDto::new).toList();
    }

    public void cadastrarPet(String idOuNome, PetRequestDto dto){
        Abrigo abrigo = buscarAbrigo(idOuNome);
        Pet pet = new Pet(dto);
        pet.setAbrigo(abrigo);
        petRepository.save(pet);
        abrigo.getPets().add(pet);
        repository.save(abrigo);
    }

    public Abrigo buscarAbrigo(String idOuNome) {
        Optional<Abrigo> optional;
        try {
            Long id = Long.parseLong(idOuNome);
            optional = repository.findById(id);
        } catch (NumberFormatException exception) {
            optional = repository.findByNome(idOuNome);
        }

        return optional.orElseThrow(() -> new ValidacaoException("Abrigo não encontrado"));
    }

    public List<AbrigoResponseDto> findAll() {
        return repository.findAll().stream().map(AbrigoResponseDto::new).toList();
    }
}
