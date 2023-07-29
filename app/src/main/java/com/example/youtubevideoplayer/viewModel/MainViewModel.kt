import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubevideoplayer.network.Service.RetrofitService
import com.example.youtubevideoplayer.network.Service.YoutubeItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val retrofitService: RetrofitService) : ViewModel() {

    private val _youtubeItems = MutableLiveData<List<YoutubeItem>>()
    val youtubeItems: LiveData<List<YoutubeItem>> get() = _youtubeItems

    init {
        // ViewModel이 생성될 때 데이터를 가져옵니다.
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = retrofitService.getYoutubeItemList()
                response.enqueue(object : Callback<ArrayList<YoutubeItem>> {
                    override fun onResponse(call: Call<ArrayList<YoutubeItem>>, response: Response<ArrayList<YoutubeItem>>) {
                        if (response.isSuccessful) {
                            _youtubeItems.value = response.body() ?: emptyList()
                        } else {
                            // 에러 처리
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<YoutubeItem>>, t: Throwable) {
                        // 에러 처리
                    }
                })
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }
}