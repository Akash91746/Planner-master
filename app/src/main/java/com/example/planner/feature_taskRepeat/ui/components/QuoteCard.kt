package com.example.planner.feature_taskRepeat.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.planner.feature_quotes.domain.models.Quote
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.ui.theme.elevation
import com.example.planner.ui.theme.spacing

@Composable
fun QuoteCard(
    modifier: Modifier = Modifier,
    data: Quote,
    contentPadding: PaddingValues = PaddingValues(MaterialTheme.spacing.medium)
) {
    Card(
        modifier = modifier,
        elevation = MaterialTheme.elevation.small,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(contentPadding)
        ) {

            Text(
                text = "Quote of the day",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
            )

            Text(text = "\"${data.quote}\"", style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.padding(bottom = MaterialTheme.spacing.small))


            Text(text = "-${data.author}", style = MaterialTheme.typography.body2)


        }
    }
}

@Preview
@Composable
fun PreviewQuoteCard() {
    PlannerTheme {
        QuoteCard(data = Quote(quote = "This is Today's Quote", author = "Akash"))
    }
}