import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basictodoapp.Repository
import com.example.basictodoapp.TaskViewModel

class TaskViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
