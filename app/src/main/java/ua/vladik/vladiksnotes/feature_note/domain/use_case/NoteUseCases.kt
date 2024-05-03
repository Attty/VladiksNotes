package ua.vladik.vladiksnotes.feature_note.domain.use_case

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase,
    val getTrashNotesUseCase: GetTrashNotesUseCase,
    val addTrashNoteUseCase: AddTrashNoteUseCase,
    val deleteTrashNoteUseCase: DeleteTrashNoteUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val getTrashNoteUseCase: GetTrashNoteUseCase

)
