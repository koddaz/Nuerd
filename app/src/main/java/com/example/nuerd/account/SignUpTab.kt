package com.example.nuerd.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.nuerd.account.models.CustomTextField
import com.example.nuerd.models.AuthViewModel
import com.example.nuerd.models.Country
import com.example.nuerd.models.Flags
import com.example.nuerd.models.Name
import com.example.nuerd.models.getCountriesViewModel
import com.example.nuerd.ui.theme.NuerdTheme

@Composable
fun SignUp(getCountries: getCountriesViewModel, countriesList: List<Country>?, authViewModel: AuthViewModel?, navOnLogin: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val countries = countriesList
    val scrollState = rememberScrollState()

    LaunchedEffect(countries) {
        if (countries != null) {
            if (countries.isEmpty()) {
                getCountries.loadCountries()
            }
        }
    }

    Column(modifier = Modifier.background(colorScheme.secondary).padding(8.dp)) {
        CustomTextField(
            onValueChange = { username = it },
            label = "Username",
            value = username,
            keyboardType = KeyboardType.Text
        )
        CustomTextField(
            onValueChange = { email = it },
            label = "E-mail",
            value = email,
            keyboardType = KeyboardType.Email
        )
        CustomTextField(
            onValueChange = { password = it },
            label = "Password",
            value = password,
            keyboardType = KeyboardType.Password
        )

        Box {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 3.dp,
                            color = colorScheme.onPrimary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(1f)) {
                        Text(
                            "${country.ifEmpty { "Country" }}", color = colorScheme.onPrimary,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.MoreVert, tint = colorScheme.onPrimary, contentDescription = "More options")
                        }
                    }
                }
                if (expanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 4.dp)
                            .border(
                                width = 2.dp,
                                color = colorScheme.onPrimary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .height(200.dp)
                            .verticalScroll(scrollState),


                    ) {
                        countries?.forEach { countryItem ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 8.dp)
                                    .clickable {
                                        country = countryItem.name.common ?: ""
                                        expanded = false
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = rememberImagePainter(data = countryItem.flags.png),
                                    contentDescription = "Flag of ${countryItem.name.common}",
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = countryItem.name.common ?: "",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 8.dp),
                                    color = colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        EditButton(
            onClick = {
                authViewModel?.signUp(email, password, username, country)
                navOnLogin()
            },
            title = "Sign Up",
            icon = Icons.Filled.PersonAddAlt
        )

    }
}

@Preview
@Composable
fun SignUpPreview() {
    val mockCountries = listOf(
        Country(Name("USA"), "US", Flags("https://example.com/usa.png")),
        Country(Name("Canada"), "CA", Flags("https://example.com/canada.png")),
        Country(Name("Mexico"), "MX", Flags("https://example.com/mexico.png")),
        Country(Name("Mexico"), "MX", Flags("https://example.com/mexico.png")),
        Country(Name("Mexico"), "MX", Flags("https://example.com/mexico.png")),
        Country(Name("Mexico"), "MX", Flags("https://example.com/mexico.png")),
        Country(Name("USA"), "US", Flags("https://example.com/usa.png")),
        Country(Name("Canada"), "CA", Flags("https://example.com/canada.png")),
        Country(Name("Mexico"), "MX", Flags("https://example.com/mexico.png")),
        Country(Name("Mexico"), "MX", Flags("https://example.com/mexico.png")),
        Country(Name("Mexico"), "MX", Flags("https://example.com/mexico.png")),
        Country(Name("Mexico"), "MX", Flags("https://example.com/mexico.png")),
    )

    val mockViewModel = object : getCountriesViewModel() {
        init {
            val countriesLiveData = MutableLiveData<List<Country>>()
            countriesLiveData.value = mockCountries
            countries = countriesLiveData
        }
    }

    NuerdTheme {
        SignUp(
            authViewModel = null,
            getCountries = mockViewModel,
            navOnLogin = { },
            countriesList = mockCountries


        )
    }
}