    package africa.semicolon.services;

    import africa.semicolon.data.model.Note;
    import africa.semicolon.data.model.User;
    import africa.semicolon.data.repositories.NoteRepository;
    import africa.semicolon.data.repositories.UserRepository;
    import africa.semicolon.dtos.requests.CreateNoteRequest;
    import africa.semicolon.dtos.requests.DeleteNoteRequest;
    import africa.semicolon.dtos.requests.EditNoteRequest;
    import africa.semicolon.dtos.responds.CreateNoteResponse;
    import africa.semicolon.dtos.responds.DeleteNoteResponse;
    import africa.semicolon.dtos.responds.EditNoteResponse;
    import africa.semicolon.noteException.BigNoteManagementException;
    import africa.semicolon.noteException.NoteNotFoundExceptionException;
    import africa.semicolon.noteException.UserNotFoundException;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    import static africa.semicolon.utils.NoteMapper.*;

    @Service
    public class NoteServiceImpl implements NoteService {

        @Autowired
        private NoteRepository noteRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private  UserService userService;

        @Override
        public CreateNoteResponse createNoteForUser(CreateNoteRequest createNoteRequest) {
            User user = findUserBy(createNoteRequest.getUserId());
            checkUserStatus(user.getUsername());
            validateCreateNoteRequest(createNoteRequest);
            Note newNote = mapNoteForCreate(createNoteRequest, user);
            Note savedNote = noteRepository.save(newNote);
            return mapCreateNoteResponse(savedNote);
        }

        @Override
        public EditNoteResponse editNoteForUser(EditNoteRequest editNoteRequest) {
            User user = findUserBy(editNoteRequest.getUserId());
            checkUserStatus(user.getUsername());
            Note existingNote = findNoteForEdit(editNoteRequest, user);
            updateNoteWithEditRequest(editNoteRequest, existingNote);
            Note updatedNote = noteRepository.save(existingNote);
            return mapEditNoteResponse(updatedNote);
        }

        @Override
        public DeleteNoteResponse deleteNoteForUser(DeleteNoteRequest deleteNoteRequest) {
            User user = findUserBy(deleteNoteRequest.getUserId());
            checkUserStatus(user.getUsername());
            Note existingNote = findNoteForDelete(deleteNoteRequest, user);
            noteRepository.delete(existingNote);
            return mapDeleteNoteResponse(existingNote);
        }

        @Override
        public List<Note> getAllNoteByCategory(String userId, String category) {
            User user = userRepository.findUserById(userId);
            if (user == null) {
                throw new UserNotFoundException("User not found with ID: " + userId);
            }

            return noteRepository.findByUserIdAndCategory(userId, category);
        }

        @Override
        public Optional<Note> getAllNotesByUserId(String userId) {
            User user = userRepository.findUserById(userId);
            if (user == null) {
                throw new UserNotFoundException("User not found with ID: " + userId);
            }
            return noteRepository.findByUserId(userId);
        }

        @Override
        public User findUserBy(String userId) {
            User user = userRepository.findUserById(userId);
            if (user == null) {
                throw new UserNotFoundException("User not found with username: " + userId);
            }
            return user;
        }

        private void validateCreateNoteRequest(CreateNoteRequest createNoteRequest) {
            String userId = createNoteRequest.getUserId();
            User user = findUserBy(userId);
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                throw new BigNoteManagementException("Username cannot be null or empty");
            }

            String noteTitle = createNoteRequest.getTitle();
            boolean noteExistsForUser = noteRepository.existsByTitleAndUserId(noteTitle, userId);
            if (noteExistsForUser) {
                throw new BigNoteManagementException("Note already exists");
            }
        }

        private void checkUserStatus(String username) {
            if (!userService.isUserRegistered(username)) {
                throw new BigNoteManagementException("User with username " + username + " is not registered");
            }

            if (!userService.isUserLoggedIn(username)) {
                throw new BigNoteManagementException("User with username " + username + " is not logged in");
            }
        }

        private Note findNoteForEdit(EditNoteRequest editNoteRequest, User user) {
            String noteId = editNoteRequest.getNoteId();
            Optional<Note> existingNoteOptional = noteRepository.findNoteByNoteIdAndUserId(noteId, user.getId());
            if (!existingNoteOptional.isPresent()) {
                throw new NoteNotFoundExceptionException("Note with ID " + noteId + " not found");
            }

            Note existingNote = existingNoteOptional.get();
            if (!existingNote.getUserId().equals(user.getId())) {
                throw new BigNoteManagementException("You are not authorized to edit this note");
            }
            return existingNote;
        }

        private Note findNoteForDelete(DeleteNoteRequest deleteNoteRequest, User user) {
            String noteId = deleteNoteRequest.getNoteId();
            Optional<Note> existingNoteOptional = noteRepository.findNoteByNoteIdAndUserId(noteId, user.getId());
            if (!existingNoteOptional.isPresent()) {
                throw new NoteNotFoundExceptionException("Note with ID " + noteId + " not found");
            }

            Note existingNote = existingNoteOptional.get();
            if (!existingNote.getUserId().equals(user.getId())) {
                throw new BigNoteManagementException("You are not authorized to delete this note");
            }
            return existingNote;
        }

    }
