package com.coderizzard.batterymonitorer.ui.viewmodel

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.coderizzard.batterymonitorer.NavRoute
import com.coderizzard.batterymonitorer.db.dao.RespondentDao
import com.coderizzard.batterymonitorer.db.entity.Respondent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnBoardingFormViewModel : ViewModel() {

    var _formState = MutableStateFlow(OnBoardingFormData(
        firstName = FormFieldData("", validator = {
            when {
                it.length > 30 -> "Too long."
                it.isEmpty() -> "First name is required."
                else -> null
            }
        }),
        lastName = FormFieldData("", validator = {
            when {
                it.length > 30 -> "Too long."
                it.isEmpty() -> "Last name is required."
                else -> null
            }
        }),
        program = FormFieldData("", validator = {
            when {
                it.length > 30 -> "Too long."
                it.isEmpty() -> "Program is required."
                else -> null
            }
        }),
        section = FormFieldData("", validator = {
            when {
                it.length > 4 -> "Too long."
                it.isEmpty() -> "Section is required."
                else -> null
            }
        }),
        year = FormFieldData("", validator = {
            when {
                it.length > 2 -> "Too long."
                it.isEmpty() -> "Year is required."
                else -> null
            }
        }),
    ))

    val formState = _formState.asStateFlow()

    fun onEvent(event: OnBoardingFormEvent) {
        when(event) {
            is OnBoardingFormEvent.FirstNameChanged -> {
                _formState.update {  state ->
                    state.copy(
                        firstName = state.firstName.setValue(event.s).validate()
                    )
                }
            }
            is OnBoardingFormEvent.LastNameChanged -> {
                _formState.update {  state ->
                    state.copy(
                        lastName = state.lastName.setValue(event.s).validate()
                    )
                }
            }
            is OnBoardingFormEvent.ProgramChanged -> {
                _formState.update {  state ->
                    state.copy(
                        program = state.program.setValue(event.s).validate()
                    )
                }
            }
            is OnBoardingFormEvent.SectionChanged -> {
                _formState.update {  state ->
                    state.copy(
                        section = state.section.setValue(event.s).validate()
                    )
                }
            }
            is OnBoardingFormEvent.YearChanged -> {
                _formState.update {  state ->
                    state.copy(
                        year = state.year.setValue(event.s).validate()
                    )
                }
            }
            is OnBoardingFormEvent.FormSubmit -> {
                _formState.update { state ->
                    state.copy(
                        firstName = state.firstName.validate(),
                        lastName = state.lastName.validate(),
                        section = state.section.validate(),
                        year = state.year.validate(),
                        program = state.program.validate()
                    ).also {
                        if(it.isValid()) {
                            Log.d("FormEvent", "SUBMIT")
                            viewModelScope.launch {
                                val rsp = Respondent(
                                    id = 2022104499,
                                    year = it.year.data.toInt(),
                                    program = it.program.data,
                                    lastName = it.lastName.data,
                                    section = it.section.data,
                                    firstName = it.firstName.data,
                                )
                                event.dao.createRespondent(rsp)
                                event.rpViewModel.update(rsp)
                            }
                        }
                    }
                }
            }
        }
    }



}



sealed interface OnBoardingFormEvent {
    data class FormSubmit(
        val nav: NavController,
        val dao: RespondentDao,
        val rpViewModel: RespondentStateViewModel,
    ) : OnBoardingFormEvent
    data class FirstNameChanged(val s : String) : OnBoardingFormEvent
    data class LastNameChanged(val s : String) : OnBoardingFormEvent
    data class ProgramChanged(val s : String) : OnBoardingFormEvent
    data class SectionChanged(val s : String) : OnBoardingFormEvent
    data class YearChanged(val s : String) : OnBoardingFormEvent
}


@Composable
fun TextFormField(
    modifier: Modifier = Modifier,
    fieldData: FormFieldData<String>,
    onValueChange : (s : String) -> Unit,
    label: @Composable () -> Unit,
) {
    TextField(
        value = fieldData.data,
        onValueChange = onValueChange,
        label = label,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        isError = !fieldData.isError(),

    )
    if(fieldData.errorMessage != null) {
        Text(
            text = fieldData.errorMessage,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

data class FormFieldData<T>(
    val data : T,
    val errorMessage : String? = null,
    val validator: (T) -> String?,
) {
    fun isValid(): Boolean {
        return errorMessage == null
    }
    fun isError() : Boolean {
        return errorMessage != null
    }
    fun setValue(v : T): FormFieldData<T> {
        return this.copy(
            data = v
        )
    }
    fun validate() : FormFieldData<T> {
        return this.copy(
            errorMessage = validator(data)
        )
    }
}

data class OnBoardingFormData(
    val firstName : FormFieldData<String>,
    val lastName : FormFieldData<String>,
    val program : FormFieldData<String>,
    val section : FormFieldData<String>,
    val year : FormFieldData<String>,
) {
    fun isValid() : Boolean {
        return listOf(
            firstName.errorMessage,
            lastName.errorMessage,
            program.errorMessage,
            section.errorMessage,
            year.errorMessage
        ).all { it == null }
    }
}