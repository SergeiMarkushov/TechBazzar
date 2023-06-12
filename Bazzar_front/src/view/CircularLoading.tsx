import { CircularProgress } from "@mui/material";
import Box from "@mui/material/Box/Box";
import React from 'react';

export function CircularLoading() {
	return (
		<Box sx={{ display: 'flex' }}>
			<CircularProgress />
		</Box>
	);
}