package ncodedev.coffeebase.ui.screens.editcoffee

import android.content.Context
import android.graphics.Bitmap
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.Tag
import ncodedev.coffeebase.model.enums.Continent
import ncodedev.coffeebase.model.enums.RoastProfile
import okhttp3.MultipartBody
import org.junit.Before
import org.junit.Test
import java.io.FileOutputStream
import java.nio.file.Files

@OptIn(ExperimentalCoroutinesApi::class)
class EditCoffeeViewModelTest {

    private lateinit var viewModel: EditCoffeeViewModel

    @MockK
    private lateinit var repository: CoffeeRepository

    @MockK
    private lateinit var context: Context

    @MockK
    private lateinit var coffeeImage: Bitmap

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = EditCoffeeViewModel(repository)
        coEvery { repository.saveCoffee(any(), any()) } returns Unit
    }

    @Test
    fun saveCoffee_doesNotCallRepository_whenCoffeeNameIsEmpty() = runTest {
        viewModel.coffeeName.value = ""
        viewModel.saveCoffee(context, null)
        coVerify(exactly = 0) { repository.saveCoffee(any(), any()) }
    }

    @Test
    fun saveCoffee_doesNotCallRepository_whenCoffeeCropHeightIsInvalid() = runTest {
        viewModel.coffeeName.value = "test coffee"
        viewModel.cropHeight.value = "9999999"
        viewModel.saveCoffee(context, null)
        coVerify(exactly = 0) { repository.saveCoffee(any(), any()) }
    }

    @Test
    fun saveCoffee_doesNotCallRepository_whenCoffeeScaRatingIsInvalid() = runTest {
        viewModel.coffeeName.value = "test coffee"
        viewModel.scaRating.value = "9999999"
        viewModel.saveCoffee(context, null)
        coVerify(exactly = 0) { repository.saveCoffee(any(), any()) }
    }

    @Test
    fun saveCoffee_callRepository_withProperCoffeeObjectNameOnly() = runTest {
        viewModel.coffeeName.value = "test coffee"
        viewModel.saveCoffee(context, null)
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.saveCoffee(match { it.name == "test coffee" }, null) }
    }

    @Test
    fun saveCoffee_callRepository_withProperCoffeeObjectAllParams() = runTest {
        viewModel.coffeeName.value = "test coffee"
        viewModel.origin.value = "test origin"
        viewModel.roaster.value = "test roaster"
        viewModel.processing.value = "test processing"
        viewModel.roastProfile.value = RoastProfile.LIGHT
        viewModel.region.value = "test region"
        viewModel.continent.value = Continent.AFRICA
        viewModel.farm.value = "test farm"
        viewModel.cropHeight.value = "1000"
        viewModel.scaRating.value = "90"
        viewModel.rating.doubleValue = 4.5
        viewModel.tags.addAll(listOf(Tag(1, "tag1", "#FF0000"), Tag(2, "tag2", "#00FF00")))

        viewModel.saveCoffee(context, null)
        advanceUntilIdle()

        coVerify(exactly = 1) {
            repository.saveCoffee(match {
                it.name == "test coffee" &&
                        it.origin == "test origin" &&
                        it.roaster == "test roaster" &&
                        it.processing == "test processing" &&
                        it.roastProfile == RoastProfile.LIGHT.roastProfileValue &&
                        it.region == "test region" &&
                        it.continent == Continent.AFRICA.continentValue &&
                        it.farm == "test farm" &&
                        it.cropHeight == 1000 &&
                        it.scaRating == 90 &&
                        it.rating == 4.5
                it.tags.size == 2
            }, null)
        }
    }

    @Test
    fun saveCoffee_coffeeImagePart_isNullWhenNoPhoto() = runTest {
        viewModel.coffeeName.value = "test"
        viewModel.saveCoffee(context, null)
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.saveCoffee(any(), null) }
    }

    @Test
    fun saveCoffee_coffeeImagePart_isConvertedToMultipartBodyPartWhenImageIsPresent() = runTest {
        val tempFile = Files.createTempFile("test", ".jpg").toFile()

        // Setup mockk behavior
        every { context.cacheDir } returns tempFile.parentFile

        every { coffeeImage.compress(any(), any(), any()) } answers {
            val fos = arg<FileOutputStream>(2)
            fos.use { it.write(ByteArray(0)) }
            true
        }

        viewModel.coffeeName.value = "test"
        viewModel.saveCoffee(context, coffeeImage)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            repository.saveCoffee(any(), match {
                        it is MultipartBody.Part &&
                        it.headers?.get("Content-Disposition")?.contains("name=\"image\"") == true
            })
        }
        tempFile.delete()
    }
}
